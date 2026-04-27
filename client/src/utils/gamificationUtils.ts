export interface CivicUser {
  id: string;
  name: string;
  points: number;
  neighborhood: string;
}

// Titlul bazat pe puncte acumulate (afisat in cyan)
export const getTitleByPoints = (points: number): string => {
  if (points >= 300) return "Erou Local";
  if (points >= 150) return "Gardian al Mediului";
  if (points >= 50) return "Cetățean Activ";
  return "Observator";
};

// Beneficiile exclusive bazate pe pozitia in clasament
export const getTopTierPerks = (rankIndex: number) => {
  // rankIndex 0 = Locul 1, 1 = Locul 2, 2 = Locul 3
  const isVerified = rankIndex < 3;

  // Fiecare loc are un endpoint de download diferit
  let downloadLabel: string | null = null;
  let downloadUrl: string | null = null;

  if (rankIndex === 0) {
    downloadLabel = "⬇ Descarcă CSV Complet (toate datele)";
    downloadUrl = "http://localhost:8080/api/export/masuratori/csv";
  } else if (rankIndex === 1) {
    downloadLabel = "⬇ Descarcă CSV Ultimele 7 Zile";
    downloadUrl = "http://localhost:8080/api/export/masuratori/7zile/csv";
  } else if (rankIndex === 2) {
    downloadLabel = "⬇ Descarcă CSV Ultimele 24 Ore";
    downloadUrl = "http://localhost:8080/api/export/masuratori/24ore/csv";
  }

  let specialBadge: string | null = null;
  if (rankIndex === 0) specialBadge = "🏆 Vocea Cartierului";
  else if (rankIndex === 1) specialBadge = "🥈 Investigator";
  else if (rankIndex === 2) specialBadge = "🥉 Vigilent";

  return { isVerified, downloadLabel, downloadUrl, specialBadge };
};
