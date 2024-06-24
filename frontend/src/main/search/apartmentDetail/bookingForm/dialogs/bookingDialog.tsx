import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useBookingContext } from '@/context/booking/bookingContext';
import { Box, CircularProgress, Typography } from '@mui/material';
import { useEntitiesContext } from '@/context/entities/useEntitiesContext';
import { BuchungCreate, Ferienwohnung } from '@/types';
import BookingResultDialog from './bookingResultDialog';
import { useNavigate } from 'react-router-dom';
import { getSearchtUrl } from '@/assets/appEndpoints';
import dayjs from 'dayjs';

interface BookingDialogProps {
    open: boolean;
    setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

const BookingDialog: React.FC<BookingDialogProps> = ({
    open,
    setOpen
}) => {
    const {
        bookingFerienwohung,
        startdatum,
        enddatum,
        getDateDifferenceDays
    } = useBookingContext();
    const {
        addBuchung,
        filterFerienwohnungen,
        filteredFerienwohnungen,
        filteredFerienwohnungenLoading
    } = useEntitiesContext();
    const navigate = useNavigate();

    const [loading, setLoading] = React.useState(false);
    const [bookingSuccess, setBookingSuccess] = React.useState(false);
    const [bookingFailure, setBookingFailure] = React.useState(false);
    const [bookingFailureErrorMessage, setBookingFailureErrorMessage] = React.useState("");

    const handleClose = () => {
        if (!loading) {
            setOpen(false);
        }
    };

    const validateBookingData = async (bookingFerienwohung: Ferienwohnung, startdatum: dayjs.Dayjs, enddatum: dayjs.Dayjs): Promise<boolean> => {
        let isValid = true;
        // booking span has to be at least three days
        if (getDateDifferenceDays() <= 3) { isValid = false; setBookingFailureErrorMessage("Aufenthaltsdauer muss mehr als 3 Tage betragen"); return isValid; }
        // check if available
        await filterFerienwohnungen(undefined, undefined, startdatum.format('YYYY-MM-DD'), enddatum.format('YYYY-MM-DD'));
        while (filteredFerienwohnungenLoading) { }
        const availableWohnung = filteredFerienwohnungen.find((filteredWohohnung) => filteredWohohnung.ferienwohnungs_id === bookingFerienwohung.ferienwohnungs_id);
        if (availableWohnung === undefined) { isValid = false; setBookingFailureErrorMessage("Im gewünschten Zeitraum nicht verfügbar"); return isValid; }

        return isValid;
    }

    const onBookingFailure = () => {
        setLoading(false);
        setBookingFailure(true);
    }

    const onBookingSuccess = () => {
        setBookingFailureErrorMessage("");
        setLoading(false);
        setBookingSuccess(true);
        handleClose();
    }

    const handleConfirmBookingButtonClicked = async () => {
        setLoading(true);
        if (bookingFerienwohung && startdatum && enddatum) {
            const isValidBooking = await validateBookingData(bookingFerienwohung, startdatum, enddatum);
            if (!isValidBooking) { onBookingFailure(); return }

            const newBuchung: BuchungCreate = {
                ferienwohnungs_id: bookingFerienwohung.ferienwohnungs_id,
                email: 'luna.sommer@hotmail.com',
                startdatum: startdatum.format('YYYY-MM-DD'),
                enddatum: enddatum.format('YYYY-MM-DD'),
                rechnungsbetrag: getDateDifferenceDays() * bookingFerienwohung.mietpreis,
            };

            try {
                await addBuchung(newBuchung);
                onBookingSuccess()
            } catch (error) {
                onBookingFailure();
            }
        } else {
            onBookingFailure();
        }
    };

    return (
        <>
            {bookingFerienwohung &&
                <Dialog
                    open={open}
                    fullWidth={true}
                    onClose={handleClose}
                    aria-labelledby="alert-dialog-title"
                    aria-describedby="alert-dialog-description"
                >
                    <DialogTitle id="alert-dialog-title">
                        <Typography variant='h5'>{"Ferienwohnung Buchung"}</Typography>
                    </DialogTitle>
                    <DialogContent>
                        <DialogContentText id="alert-dialog-description">
                            <Box sx={{ pb: 2 }}>
                                <Typography>{`${bookingFerienwohung?.ferienwohnungsname}`}</Typography>
                                <Typography>{`${startdatum?.format("DD.MM.YYYY")}-${enddatum?.format("DD.MM.YYYY")} (${getDateDifferenceDays()} Nächte)`}</Typography>
                                <Typography>{`Gesamtpreis: ${getDateDifferenceDays() * bookingFerienwohung?.mietpreis}€`}</Typography>
                            </Box>
                            <Typography>{`Möchten Sie die Ferienwohnung wirklich buchen?`}</Typography>
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button variant='outlined' onClick={handleClose} disabled={loading}>Abbrechen</Button>
                        <Box sx={{ m: 1, position: 'relative' }}>
                            <Button
                                variant='contained'
                                onClick={handleConfirmBookingButtonClicked}
                                disabled={loading}
                                autoFocus
                            >
                                Buchung bestätigen
                            </Button>
                            {loading && (
                                <CircularProgress
                                    size={24}
                                    sx={{
                                        color: "primary",
                                        position: 'absolute',
                                        top: '50%',
                                        left: '50%',
                                        marginTop: '-12px',
                                        marginLeft: '-12px',
                                    }}
                                />
                            )}
                        </Box>
                    </DialogActions>
                </Dialog>
            }
            <BookingResultDialog
                bookingSuccess={bookingSuccess}
                bookingFailure={bookingFailure}
                bookingFailureErrorMessage={bookingFailureErrorMessage}
                onSuccessClose={() => { setBookingSuccess(false); navigate(getSearchtUrl()); }}
                onFailureClose={() => { setBookingFailure(false); setBookingFailureErrorMessage(""); }}
            />
        </>
    );
}

export default BookingDialog;
