import axios, { AxiosResponse, AxiosError } from 'axios';
import { Ferienwohnung, Kunde } from '../types';

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

export const addFerienwohnung = async (newFerienwohnung: Ferienwohnung): Promise<void> => {
    try {
        await axiosInstance.post('/ferienwohnung', newFerienwohnung);
    } catch (error) {
        throw new Error(`Failed to add Ferienwohnung: ${error}`);
    }
};

export const addKunde = async (newKunde: Kunde): Promise<void> => {
    try {
        await axiosInstance.post('/kunde', newKunde);
    } catch (error) {
        throw new Error(`Failed to add Kunde: ${error}`);
    }
};

export const fetchFerienwohnungen = async (): Promise<Ferienwohnung[]> => {
    try {
        const response = await axiosInstance.get<Ferienwohnung[]>('/ferienwohnung');
        return response.data;
    } catch (error) {
        throw new Error(`Failed to fetch Ferienwohnungen: ${error}`);
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

export default axiosInstance;
