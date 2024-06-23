import React, { createContext, useContext, useState } from 'react';
import dayjs from 'dayjs';

interface BookingContextProps {
    startdatum: dayjs.Dayjs | null;
    enddatum: dayjs.Dayjs | null;
    setStartdatum: (date: dayjs.Dayjs | null) => void;
    setEnddatum: (date: dayjs.Dayjs | null) => void;
    handleStartDateChange: (date: dayjs.Dayjs | null) => void;
    handleEndDateChange: (date: dayjs.Dayjs | null) => void;
    disableDatesBeforeOrExactStartDate: (date: dayjs.Dayjs) => boolean;
}

const BookingContext = createContext<BookingContextProps | undefined>(undefined);

export const useBookingContext = () => {
    const context = useContext(BookingContext);
    if (!context) {
        throw new Error('useBookingContext must be used within a BookingProvider');
    }
    return context;
};

export const BookingProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [startdatum, setStartdatum] = useState<dayjs.Dayjs | null>(null);
    const [enddatum, setEnddatum] = useState<dayjs.Dayjs | null>(null);

    const handleStartDateChange = (date: dayjs.Dayjs | null) => {
        if (date) {
            const newStartdatum = dayjs(date);
            setStartdatum(newStartdatum);
            const newEnddatum = newStartdatum.add(1, 'day');
            setEnddatum(newEnddatum);
        } else {
            setStartdatum(null);
            setEnddatum(null);
        }
    };

    const handleEndDateChange = (date: dayjs.Dayjs | null) => {
        setEnddatum(date ? dayjs(date) : null);
    };

    const disableDatesBeforeOrExactStartDate = (date: dayjs.Dayjs) => {
        if (!startdatum) return false; // No startdatum selected, allow all dates
        return dayjs(date).isSame(dayjs(startdatum), 'day') || dayjs(date).isBefore(dayjs(startdatum), 'day');
    };

    return (
        <BookingContext.Provider value={{ startdatum, enddatum, setStartdatum, setEnddatum, handleStartDateChange, handleEndDateChange, disableDatesBeforeOrExactStartDate }}>
            {children}
        </BookingContext.Provider>
    );
};
