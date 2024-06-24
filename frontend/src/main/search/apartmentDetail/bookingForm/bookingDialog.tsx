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
import { Buchung } from '@/types';
import ProcessBookingDialog from './processBookingDialog';
import { useNavigate } from 'react-router-dom';
import { getSearchtUrl } from '@/assets/appEndpoints';

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
        addBuchung
    } = useEntitiesContext();
    const navigate = useNavigate();

    const [loading, setLoading] = React.useState(false);
    const [bookingSuccess, setBookingSuccess] = React.useState(false);
    const [bookingFailure, setBookingFailure] = React.useState(false);

    const handleClose = () => {
        if (!loading) {
            setOpen(false);
        }
    };

    const handleConfirmBookingButtonClicked = async () => {
        if (bookingFerienwohung) {
            setLoading(true);
            const newBuchung: Buchung = {
                buchnungsnummer: 0,  // Dummy value
                ferienwohnungs_id: bookingFerienwohung.ferienwohnungs_id,
                email: 'luna.sommer@hotmail.com',
                buchungsdatum: new Date().toISOString().split('T')[0],
                startdatum: startdatum?.format('YYYY-MM-DD') || '',
                enddatum: enddatum?.format('YYYY-MM-DD') || '',
                sterne: 0, // Dummy value
                bewertungsdatum: '',  // Dummy value
                rechnungsnummer: Math.floor(Math.random() * 99999) + 1,
                rechnungsbetrag: getDateDifferenceDays() * bookingFerienwohung.mietpreis,
                rechnungsdatum: new Date().toISOString().split('T')[0],
            };

            try {
                await addBuchung(newBuchung);
                setLoading(false);
                setBookingSuccess(true);
                handleClose();
            } catch (error) {
                setLoading(false);
                setBookingFailure(true);
            }
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
            <ProcessBookingDialog
                bookingSuccess={bookingSuccess}
                bookingFailure={bookingFailure}
                onSuccessClose={() => { setBookingSuccess(false); navigate(getSearchtUrl()); }}
                onFailureClose={() => { setBookingFailure(false); }}
            />
        </>
    );
}

export default BookingDialog;
