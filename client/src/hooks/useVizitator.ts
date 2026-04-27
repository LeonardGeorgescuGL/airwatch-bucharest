import { useState, useEffect } from 'react';

/**
 * Hook pentru sesiunea vizitatorului.
 * Genereaza un UUID unic in sessionStorage - se sterge automat cand se inchide tab-ul.
 * NU se salveaza nimic in baza de date.
 */
export const useVizitator = () => {
  const [sessionId] = useState<string>(() => {
    const existing = sessionStorage.getItem('airwatch_vizitator_id');
    if (existing) return existing;
    // Genereaza UUID simplu fara dependinte externe
    const newId = 'viz-' + Math.random().toString(36).substring(2, 9) + '-' + Date.now().toString(36);
    sessionStorage.setItem('airwatch_vizitator_id', newId);
    return newId;
  });

  const [startTime] = useState<number>(() => {
    const t = sessionStorage.getItem('airwatch_start_time');
    if (t) return parseInt(t);
    const now = Date.now();
    sessionStorage.setItem('airwatch_start_time', now.toString());
    return now;
  });

  useEffect(() => {
    // Curata sessionStorage la inchiderea tab-ului
    const cleanup = () => {
      sessionStorage.removeItem('airwatch_vizitator_id');
      sessionStorage.removeItem('airwatch_start_time');
    };
    window.addEventListener('beforeunload', cleanup);
    return () => window.removeEventListener('beforeunload', cleanup);
  }, []);

  const durataSesiuneMinute = Math.floor((Date.now() - startTime) / 60000);

  return { sessionId, durataSesiuneMinute };
};
