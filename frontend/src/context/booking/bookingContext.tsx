import React, { createContext, useContext, useState } from 'react';
import dayjs from 'dayjs';
import { Ferienwohnung } from '@/types';

interface BookingContextProps {
    bookingFerienwohung: Ferienwohnung | null;
    startdatum: dayjs.Dayjs | null;
    enddatum: dayjs.Dayjs | null;
    setStartdatum: (date: dayjs.Dayjs | null) => void;
    setEnddatum: (date: dayjs.Dayjs | null) => void;
    setBookingFerienwohung: (ferienwohnung: Ferienwohnung | null) => void;
    handleStartDateChange: (date: dayjs.Dayjs | null) => void;
    handleEndDateChange: (date: dayjs.Dayjs | null) => void;
    disableDatesBeforeOrExactStartDate: (date: dayjs.Dayjs) => boolean;
    getDateDifferenceDays: () => number;
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
    const [bookingFerienwohung, setBookingFerienwohung] = useState<Ferienwohnung | null>(null);
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
        if (!startdatum) return false;
        return dayjs(date).isSame(dayjs(startdatum), 'day') || dayjs(date).isBefore(dayjs(startdatum), 'day');
    };

    const getDateDifferenceDays = () => {
        if (startdatum && enddatum) {
            return enddatum.diff(startdatum, 'day');
        }
        return 0;
    };

    return (
        <BookingContext.Provider
            value={{
                bookingFerienwohung,
                startdatum,
                enddatum,
                setBookingFerienwohung,
                setStartdatum,
                setEnddatum,
                handleStartDateChange,
                handleEndDateChange,
                disableDatesBeforeOrExactStartDate,
                getDateDifferenceDays
            }}>
            {children}
        </BookingContext.Provider>
    );
};
