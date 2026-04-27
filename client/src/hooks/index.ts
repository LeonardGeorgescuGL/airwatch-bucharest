import { useState, useEffect } from 'react';
import { api } from '../api/api';

export function useSenzori() {
    const [senzori, setSenzori] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        api.getSenzori()
            .then(data => {
                setSenzori(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setLoading(false);
            });
    }, []);

    return { senzori, loading };
}

export function useMasuratori(idSenzor: string) {
    const [masuratori, setMasuratori] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!idSenzor) return;
        
        api.getMasuratori(idSenzor)
            .then(data => {
                setMasuratori(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setLoading(false);
            });
    }, [idSenzor]);

    return { masuratori, loading };
}

export function useRapoarte() {
    const [rapoarte, setRapoarte] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        api.getRapoarte()
            .then(data => {
                setRapoarte(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setLoading(false);
            });
    }, []);

    return { rapoarte, loading };
}
