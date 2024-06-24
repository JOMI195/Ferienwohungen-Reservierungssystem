import React, { useEffect, useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Alert } from '@mui/material';

interface ProcessBookingDialogProps {
    bookingFailure: boolean;
    bookingSuccess: boolean;
    onSuccessClose: () => void;
    onFailureClose: () => void;
}

const ProcessBookingDialog: React.FC<ProcessBookingDialogProps> = ({ onSuccessClose, bookingSuccess, bookingFailure, onFailureClose }) => {
    const [open, setOpen] = useState(false);

    useEffect(() => {
        if (bookingSuccess || bookingFailure)
            setOpen(true);
        else
            setOpen(false);
    }, [bookingSuccess, bookingFailure]);

    const handleCloseButtonCklicked = () => {
        if (bookingSuccess)
            onSuccessClose();
        else if (bookingFailure)
            onFailureClose();
    }

    return (
        <Dialog
            open={open}
            onClose={onSuccessClose}
            aria-labelledby="success-dialog-title"
            aria-describedby="success-dialog-description"
        >
            <DialogTitle id="success-dialog-title">
                {bookingSuccess && <Typography variant='h5'>{"Buchung Erfolgreich"}</Typography>}
                {bookingFailure && <Typography variant='h5'>{"Buchung Fehlgeschlagen"}</Typography>}
            </DialogTitle>
            <DialogContent>
                <DialogContentText id="success-dialog-description">
                    {bookingSuccess &&
                        <Alert severity="success">
                            <Typography>{`Ihre Buchung war erfolgreich.`}</Typography>
                        </Alert>
                    }
                    {bookingFailure &&
                        <Alert severity="error">
                            <Typography>{`Ihre Buchung ist fehlgeschlagen.`}</Typography>
                        </Alert>
                    }
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button variant='contained' onClick={handleCloseButtonCklicked} autoFocus>
                    Schließen
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default ProcessBookingDialog;
