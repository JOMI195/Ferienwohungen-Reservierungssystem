import { Box, Breadcrumbs, CardMedia, Grid, IconButton, Link, Rating, Typography } from '@mui/material';
import { useParams, Link as RouterLink } from 'react-router-dom';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useEntitiesContext } from '@/context/entities/useEntitiesContext';
import LocationMap from './locationMap/locationMap';
import { useEffect } from 'react';
import BookingForm from './bookingForm/bookingForm';
import { useBookingContext } from '@/context/booking/bookingContext';

const ApartmentDetail = () => {
    const { id: ferienwohnungs_id } = useParams<{ id: string }>();
    const {
        ferienwohnungen,
        bilder,
        laender,
        ausstattungen,
        refreshFerienwohnungen,
        refreshBilder,
        refreshAusstattungen,
        refreshLaender
    } = useEntitiesContext();
    const {
        setBookingFerienwohung
    } = useBookingContext();
    const selectedFerienwohnung = ferienwohnungs_id !== undefined ? ferienwohnungen.find(ferienwohnung => ferienwohnung.ferienwohnungs_id === +ferienwohnungs_id) : undefined;
    const bild = ferienwohnungs_id !== undefined ? bilder.find(bild => bild.ferienwohnungs_id === +ferienwohnungs_id) : undefined;

    useEffect(() => {
        if (ferienwohnungen.length === 0) {
            refreshFerienwohnungen();
            refreshBilder();
            refreshAusstattungen();
            refreshLaender();
        }
    }, []);

    useEffect(() => {
        if (selectedFerienwohnung) setBookingFerienwohung(selectedFerienwohnung);
    }, [selectedFerienwohnung]);

    if (!selectedFerienwohnung) {
        return <Typography>No apartment found with ID: {ferienwohnungs_id}</Typography>;
    }

    return (
        <Box p={5}>
            <Box
                sx={{ display: "flex", justifyContent: "flex-start", alignItems: "center" }}
            >
                <IconButton sx={{ mr: 1 }} aria-label="delete" size="medium" component={RouterLink} to="/">
                    <ArrowBackIcon />
                </IconButton>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link sx={{ textDecoration: 'none' }} component={RouterLink} to="/">
                        <Typography color="GrayText">Search</Typography>
                    </Link>
                    <Typography color="primary">Detail</Typography>
                </Breadcrumbs>
            </Box>
            <Box sx={{ mt: 5 }}>
                <Typography variant="h3">{selectedFerienwohnung.ferienwohnungsname}</Typography>
                <Box sx={{ display: "flex", justifyContent: "flex-start", alignItems: "center" }}>
                    <Box sx={{ display: 'flex', justifyContent: "center" }}>
                        <Typography sx={{ mr: 1 }} variant="body2">
                            {`${selectedFerienwohnung.straße} ${selectedFerienwohnung.hausnummer}, ${selectedFerienwohnung.postleitzahl} ${selectedFerienwohnung.ort}, ${selectedFerienwohnung.landname}`}
                        </Typography>
                        <Rating size="small" value={selectedFerienwohnung.avgSterne} readOnly />
                    </Box>
                </Box>
                <Grid container spacing={2} sx={{ mt: 5 }}>
                    <Grid item xs={12} md={8}>
                        <CardMedia
                            component="img"
                            image={bild != null ? bild.linkURL : "/imagePlaceholder.svg"}
                            alt={bild != null ? "Image of the apartment" : "Default apartment banner"}
                            sx={{
                                objectFit: "cover",
                                objectPosition: "center center",
                                borderRadius: theme => theme.shape.borderRadius,
                                width: '100%',
                                maxHeight: 500
                            }}
                        />
                    </Grid>
                    <Grid item xs={12} md={4}>
                        <BookingForm selectedFerienwohnung={selectedFerienwohnung} />
                    </Grid>
                </Grid>
                <LocationMap address={`${selectedFerienwohnung.straße} ${selectedFerienwohnung.hausnummer}, ${selectedFerienwohnung.postleitzahl} ${selectedFerienwohnung.ort}, ${selectedFerienwohnung.landname}`} />
            </Box>
        </Box >
    );
};

export default ApartmentDetail;
