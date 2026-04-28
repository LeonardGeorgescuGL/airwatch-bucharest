from fastapi import APIRouter
from pydantic import BaseModel
from typing import List, Optional
import pandas as pd
from sklearn.decomposition import PCA
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler
import warnings

warnings.filterwarnings('ignore')

router = APIRouter()

class SensorData(BaseModel):
    id: str
    lat: float
    lng: float
    aqi: int
    category: str
    pm25: float
    pm10: float
    no2: float
    o3: float
    co: float
    so2: float
    dataSource: str
    cluster: Optional[int] = None

class SensorResponse(SensorData):
    healthRiskZone: str
    riskCluster: int

@router.post("/cluster", response_model=List[SensorResponse])
def perform_clustering(sensors: List[SensorData]):
    if not sensors:
        return []

    data = [{
        "pm25": s.pm25, "pm10": s.pm10, "no2": s.no2,
        "o3": s.o3, "co": s.co, "so2": s.so2, "aqi": s.aqi
    } for s in sensors]

    df = pd.DataFrame(data)
    n_clusters = 3

    if len(df) < n_clusters:
        responses = []
        for s in sensors:
            zone = "moderate-risk"
            if s.aqi > 100: zone = "high-risk"
            if s.aqi > 200: zone = "severe-risk"
            responses.append(SensorResponse(**s.dict(), healthRiskZone=zone, riskCluster=0))
        return responses

    # 1. Standardizare
    scaler = StandardScaler()
    scaled = scaler.fit_transform(df)

    # 2. PCA — reducem la 3 componente principale
    n_components = min(3, len(df) - 1, scaled.shape[1])
    pca = PCA(n_components=n_components, random_state=42)
    pca_result = pca.fit_transform(scaled)

    # 3. K-Means cu k=3 zone de risc sanitar
    kmeans = KMeans(n_clusters=n_clusters, random_state=42, n_init=10)
    clusters = kmeans.fit_predict(pca_result)

    # 4. Mapare cluster → severitate (pe baza AQI mediu per cluster)
    df['cluster_label'] = clusters
    df['aqi'] = [s.aqi for s in sensors]
    cluster_avg_aqi = df.groupby('cluster_label')['aqi'].mean().to_dict()
    sorted_clusters = sorted(cluster_avg_aqi.keys(), key=lambda k: cluster_avg_aqi[k])

    mapping = {
        sorted_clusters[0]: ("moderate-risk", 0),
        sorted_clusters[1]: ("high-risk", 1),
        sorted_clusters[2]: ("severe-risk", 2),
    }

    responses = []
    for i, s in enumerate(sensors):
        zone, risk_c = mapping[clusters[i]]
        responses.append(SensorResponse(**s.dict(), healthRiskZone=zone, riskCluster=risk_c))

    return responses
