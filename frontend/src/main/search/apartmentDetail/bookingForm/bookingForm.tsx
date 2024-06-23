import { useBookingContext } from "@/context/booking/bookingContext";
import { Box, Button, Card, Grid, Rating, Typography } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { Ferienwohnung } from '../../../../types';


interface BookingFormProps {
    selectedFerienwohnung: Ferienwohnung;
}

const BookingForm: React.FC<BookingFormProps> = ({ selectedFerienwohnung }) => {
    const {
        startdatum,
        enddatum,
        handleStartDateChange,
        handleEndDateChange,
        disableDatesBeforeOrExactStartDate,
    } = useBookingContext();

    const getDateDifference = () => {
        if (startdatum && enddatum) {
            return enddatum.diff(startdatum, 'day');
        }
        return 0;
    };

    return (
        <Card
            sx={{
                p: 2,
                width: "100%",
                height: "100%",
                borderRadius: theme => theme.shape.borderRadius,
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between"
            }}
        >
            <Box sx={{ display: "flex", flexDirection: "column", justifyContent: "flex-start", alignItems: "flex-start" }}>
                <Box sx={{ pb: 7, width: "100%", display: "flex", flexDirection: "row", justifyContent: "space-between", alignItems: "center" }}>
                    <Box sx={{ display: "flex", alignItems: "flex-end" }}>
                        <Typography component={'div'} fontWeight={'fontWeightMedium'} variant="h5">{`${selectedFerienwohnung.mietpreis}€`}</Typography>
                        <Typography variant="body1">/Nacht</Typography>
                    </Box>
                    <Rating size="small" value={selectedFerienwohnung.avgSterne} readOnly />
                </Box>
                <Grid container spacing={2} alignItems="stretch">
                    <Grid item xs={12}>
                        <DatePicker
                            format='DD-MM-YYYY'
                            label="Startdatum"
                            value={startdatum}
                            disablePast
                            onChange={(date) => handleStartDateChange(date)}
                            sx={{ width: '100%' }}
                            slotProps={{
                                field: { clearable: true },
                                day: {
                                    sx: {
                                        "&.MuiPickersDay-root.Mui-disabled": {
                                            backgroundColor: "#EDEDED",
                                        },
                                    },
                                },
                            }}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <DatePicker
                            format='DD-MM-YYYY'
                            label="Enddatum"
                            value={enddatum}
                            onChange={(date) => handleEndDateChange(date)}
                            disablePast
                            shouldDisableDate={disableDatesBeforeOrExactStartDate}
                            sx={{ width: '100%' }}
                            slotProps={{
                                field: { clearable: true },
                                day: {
                                    sx: {
                                        "&.MuiPickersDay-root.Mui-disabled": {
                                            backgroundColor: "#EDEDED",
                                        },
                                    },
                                },
                            }}
                        />
                    </Grid>
                </Grid>
            </Box>
            <Box sx={{ pt: 2, display: "flex", flexDirection: "column", justifyContent: "flex-start", alignItems: "center" }}>
                <Typography variant="h5">Gesamtpreis:</Typography>
                <Box sx={{ display: "flex", alignItems: "flex-end" }}>
                    <Typography component={'div'} fontWeight={'fontWeightBold'} variant="h4">{`${getDateDifference() * selectedFerienwohnung.mietpreis}€`}</Typography>
                    <Typography sx={{ pl: 1 }} variant="h6">{`(${getDateDifference()} Nächte)`}</Typography>
                </Box>
                <Box sx={{ pt: 2, width: "100%" }}>
                    <Button sx={{ width: "100%", borderRadius: theme => theme.shape.borderRadius }} variant="contained">Buchen</Button>
                </Box>
            </Box>
        </Card>
    );
}

export default BookingForm;
