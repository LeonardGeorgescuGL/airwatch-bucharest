const BASE_URL = "http://localhost:8080/api";

export const api = {
  // ─── SENZORI ───────────────────────────────────────────────
  getSenzori: () =>
    fetch(`${BASE_URL}/sensors`).then((r) => r.json()),

  getSenzor: (id: string) =>
    fetch(`${BASE_URL}/sensors/${id}`).then((r) => r.json()),

  getSenzoriByZona: (idZona: number) =>
    fetch(`${BASE_URL}/sensors/zona/${idZona}`).then((r) => r.json()),

  updateSenzor: (id: string, data: any) =>
    fetch(`${BASE_URL}/sensors/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    }).then((r) => r.json()),

  deleteSenzor: (id: string) =>
    fetch(`${BASE_URL}/sensors/${id}`, { method: "DELETE" }),

  // ─── MASURATORI ────────────────────────────────────────────
  getMasuratori: (idSenzor: string) =>
    fetch(`${BASE_URL}/masuratori/senzor/${idSenzor}`).then((r) => r.json()),

  getMasuratoriZona: (idZona: number) =>
    fetch(`${BASE_URL}/masuratori/zona/${idZona}`).then((r) => r.json()),

  getAllMasuratori: () =>
    fetch(`${BASE_URL}/masuratori`).then((r) => r.json()),

  // ─── ZONE URBANE ───────────────────────────────────────────
  getZone: () =>
    fetch(`${BASE_URL}/urban-areas`).then((r) => r.json()),

  getZona: (id: number) =>
    fetch(`${BASE_URL}/urban-areas/${id}`).then((r) => r.json()),

  createZona: (zona: any) =>
    fetch(`${BASE_URL}/urban-areas`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(zona),
    }).then((r) => r.json()),

  updateZona: (id: number, zona: any) =>
    fetch(`${BASE_URL}/urban-areas/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(zona),
    }).then((r) => r.json()),

  // ─── RAPOARTE CIVICE ───────────────────────────────────────
  getRapoarte: () =>
    fetch(`${BASE_URL}/rapoarte`).then((r) => r.json()),

  getRaport: (id: number) =>
    fetch(`${BASE_URL}/rapoarte/${id}`).then((r) => r.json()),

  postRaport: (raport: any) =>
    fetch(`${BASE_URL}/rapoarte`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(raport),
    }).then((r) => r.json()),

  updateRaport: (id: number, raport: any) =>
    fetch(`${BASE_URL}/rapoarte/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(raport),
    }).then((r) => r.json()),

  deleteRaport: (id: number) =>
    fetch(`${BASE_URL}/rapoarte/${id}`, { method: "DELETE" }),

  // ─── VALIDARE RAPOARTE ─────────────────────────────────────
  valideazaRaport: (id: number, request: { adminId: string; aprobat: boolean; motiv: string }) =>
    fetch(`${BASE_URL}/validare-rapoarte/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        statusRaport: request.aprobat,
        motivRespingere: request.motiv,
      }),
    }).then((r) => r.json()),

  // ─── MEMBRI / GAMIFICATION ─────────────────────────────────
  getLeaderboard: () =>
    fetch(`${BASE_URL}/membri/leaderboard`).then((r) => r.json()),

  getTopMembri: (n: number) =>
    fetch(`${BASE_URL}/membri/leaderboard`).then((r) => r.json()),

  getMembri: () =>
    fetch(`${BASE_URL}/membri`).then((r) => r.json()),

  updateMembru: (id: string, data: any) =>
    fetch(`${BASE_URL}/membri/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    }).then((r) => r.json()),

  // ─── RANG ──────────────────────────────────────────────────
  getRanguri: () =>
    fetch(`${BASE_URL}/rang`).then((r) => r.json()),

  createRang: (rang: any) =>
    fetch(`${BASE_URL}/rang`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(rang),
    }).then((r) => r.json()),

  // ─── AUTH ──────────────────────────────────────────────────
  register: (user: any) =>
    fetch(`${BASE_URL}/auth/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(user),
    }).then((r) => {
      if (!r.ok) throw new Error("Register failed");
      return r.json();
    }),

  login: (credentials: { email: string; password: string }) =>
    fetch(`${BASE_URL}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(credentials),
    }).then((r) => {
      if (!r.ok) throw new Error("Invalid credentials");
      return r.json();
    }),

  logout: () =>
    fetch(`${BASE_URL}/auth/logout`, {
      method: "POST",
      credentials: "include",
    }).then((r) => r.json()),

  getMe: () =>
    fetch(`${BASE_URL}/auth/me`, {
      credentials: "include",
    }).then((r) => {
      if (!r.ok) throw new Error("Not authenticated");
      return r.json();
    }),

  // ─── AIR QUALITY ───────────────────────────────────────────
  getAirQuality: (lat: number, lon: number) =>
    fetch(`${BASE_URL}/air-quality?lat=${lat}&lon=${lon}`).then((r) => r.json()),
};
