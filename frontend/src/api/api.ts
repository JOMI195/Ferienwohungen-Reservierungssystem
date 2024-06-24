import axios, { AxiosResponse, AxiosError } from 'axios';
import { Ausstattung, Bild, Buchung, Ferienwohnung, FerienwohnungFiltered, Kunde, Land } from '../types';

const BASE_URL = import.meta.env.VITE_API_BASE_URL;

const axiosInstance = axios.create({
    baseURL: BASE_URL,
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json',
    },
});

axiosInstance.interceptors.request.use(
    (config) => {
        // Example: config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`;
        return config;
    },
    (error: AxiosError) => {
        return Promise.reject(error);
    }
);

axiosInstance.interceptors.response.use(
    (response: AxiosResponse) => response,
    (error: AxiosError) => {
        if (error.response) {
            console.error('Response Error:', error.response.data);
            console.error('Status Code:', error.response.status);
        } else if (error.request) {
            console.error('Request Error:', error.request);
        } else {
            console.error('Error:', error.message);
        }
        return Promise.reject(error);
    }
);

export const fetchFerienwohnungen = async (): Promise<Ferienwohnung[]> => {
    try {
        const response = await axiosInstance.get<Ferienwohnung[]>('/ferienwohnung');
        return response.data;
    } catch (error) {
        throw new Error(`Failed to fetch Ferienwohnungen: ${error}`);
    }
};

export const fetchFilteredFerienwohnungen = async (
    landname?: string,
    ausstattung?: string,
    startdatum?: string,
    enddatum?: string): Promise<FerienwohnungFiltered[]> => {
    try {
        const params = new URLSearchParams();
        if (landname) params.append('landname', landname);
        if (ausstattung) params.append('ausstattung', ausstattung);
        if (startdatum) params.append('startdatum', startdatum);
        if (enddatum) params.append('enddatum', enddatum);

        const response = await axiosInstance.get<FerienwohnungFiltered[]>(`/ferienwohnung/filter?${params.toString()}`);
        return response.data;
    } catch (error) {
        throw new Error(`Failed to fetch Ferienwohnungen: ${error}`);
    }
};

export const addFerienwohnung = async (newFerienwohnung: Ferienwohnung): Promise<void> => {
    try {
        await axiosInstance.post('/ferienwohnung', newFerienwohnung);
    } catch (error) {
        throw new Error(`Failed to add Ferienwohnung: ${error}`);
    }
};

export const fetchKunden = async (): Promise<Kunde[]> => {
    try {
        const response = await axiosInstance.get<Kunde[]>('/kunde');
        return response.data;
    } catch (error) {
        throw new Error(`Failed to fetch Kunden: ${error}`);
    }
};

export const addKunde = async (newKunde: Kunde): Promise<void> => {
    try {
        await axiosInstance.post('/kunde', newKunde);
    } catch (error) {
        throw new Error(`Failed to add Kunde: ${error}`);
    }
};

export const fetchBilder = async (): Promise<Bild[]> => {
    try {
        const response = await axiosInstance.get<Bild[]>('/bild');
        return response.data;
    } catch (error) {
        throw new Error(`Failed to fetch Bilder: ${error}`);
    }
};

export const addBild = async (newBild: Bild): Promise<void> => {
    try {
        await axiosInstance.post('/bild', newBild);
    } catch (error) {
        throw new Error(`Failed to add Bild: ${error}`);
    }
};

export const fetchAusstattungen = async (): Promise<Ausstattung[]> => {
    try {
        const response = await axiosInstance.get<Ausstattung[]>('/ausstattung');
        return response.data;
    } catch (error) {
        throw new Error(`Failed to fetch Ausstattung: ${error}`);
    }
};

export const addAusstattung = async (newAusstattung: Ausstattung): Promise<void> => {
    try {
        await axiosInstance.post('/ausstattung', newAusstattung);
    } catch (error) {
        throw new Error(`Failed to add Ausstattung: ${error}`);
    }
};

export const fetchLaender = async (): Promise<Land[]> => {
    try {
        const response = await axiosInstance.get<Land[]>('/land');
        return response.data;
    } catch (error) {
        throw new Error(`Failed to fetch newLand: ${error}`);
    }
};

export const addLand = async (newLand: Land): Promise<void> => {
    try {
        await axiosInstance.post('/land', newLand);
    } catch (error) {
        throw new Error(`Failed to add newLand: ${error}`);
    }
};

export const addBuchung = async (newBuchung: Buchung): Promise<void> => {
    try {
        await axiosInstance.post('/buchung', newBuchung);
    } catch (error) {
        throw new Error(`Failed to add Buchung: ${error}`);
    }
};

export const fetchBuchungen = async (): Promise<Buchung[]> => {
    try {
        const response = await axiosInstance.get<Buchung[]>('/buchung');
        return response.data;
    } catch (error) {
        throw new Error(`Failed to fetch Buchungen: ${error}`);
    }
};

export default axiosInstance;
