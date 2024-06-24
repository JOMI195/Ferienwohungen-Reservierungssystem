import React, { createContext, useContext, useState, PropsWithChildren } from 'react';
import { fetchTouristenattraktionen, addTouristenattraktion as apiAddTouristenattraktion } from '../../api/api';
import { Touristenattraktion } from '../../types';

interface TouristenattraktionContextType {
    touristenattraktionen: Touristenattraktion[];
    touristenattraktionenLoading: boolean;
    touristenattraktionenError: string | null;
    addTouristenattraktion: (newTouristenattraktion: Touristenattraktion) => Promise<void>;
    refreshTouristenattraktionen: () => Promise<void>;
}

const TouristenattraktionContext = createContext<TouristenattraktionContextType | undefined>(undefined);

export const useTouristenattraktionContext = () => {
    const context = useContext(TouristenattraktionContext);
    if (!context) {
        throw new Error('useTouristenattraktionContext must be used within a TouristenattraktionProvider');
    }
    return context;
};

export const TouristenattraktionProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    const [touristenattraktionen, setTouristenattraktionen] = useState<Touristenattraktion[]>([]);
    const [touristenattraktionenLoading, setTouristenattraktionenLoading] = useState(false);
    const [touristenattraktionenError, setTouristenattraktionenError] = useState<string | null>(null);

    const refreshTouristenattraktionen = async (): Promise<void> => {
        try {
            setTouristenattraktionenLoading(true);
            const updatedTouristenattraktionen = await fetchTouristenattraktionen();
            setTouristenattraktionen(updatedTouristenattraktionen);
            setTouristenattraktionenLoading(false);
        } catch (error) {
            setTouristenattraktionenError('Failed to refresh Touristenattraktionen');
            setTouristenattraktionenLoading(false);
        }
    };

    const addTouristenattraktion = async (newTouristenattraktion: Touristenattraktion): Promise<void> => {
        try {
            await apiAddTouristenattraktion(newTouristenattraktion);
            await refreshTouristenattraktionen();
        } catch (error) {
            throw new Error(`Failed to add Touristenattraktion: ${error}`);
        }
    };

    return (
        <TouristenattraktionContext.Provider value={{ touristenattraktionen, touristenattraktionenLoading, touristenattraktionenError, addTouristenattraktion, refreshTouristenattraktionen }}>
            {children}
        </TouristenattraktionContext.Provider>
    );
};
