from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import warnings

# Import the routers from our split services
from kmeans_service import router as kmeans_router
from prophet_service import router as prophet_router

warnings.filterwarnings('ignore')

app = FastAPI(title="AirWatch ML Service — K-Means + Facebook Prophet")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Endpoints pentru fiecare serviciu
app.include_router(kmeans_router, tags=["Clustering"])
app.include_router(prophet_router, tags=["Forecasting"])

@app.get("/health", tags=["Health"])
def health():
    return {
        "status": "ok", 
        "service": "AirWatch ML Service", 
        "endpoints": ["/cluster", "/predict", "/health"]
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
