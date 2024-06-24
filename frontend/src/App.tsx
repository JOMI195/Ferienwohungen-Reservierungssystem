import { BrowserRouter } from 'react-router-dom';
import Routing from './routes/routing';
import { EntitiesProvider } from './context/entities/entitiesProvider';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { BookingProvider } from './context/booking/bookingContext';
import { ColorThemeProvider } from './context/colorTheme/colorThemeContext';
import { CssBaseline } from '@mui/material';

const App = () => {
  return (
    <ColorThemeProvider>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <EntitiesProvider>
          <BookingProvider>
            <BrowserRouter>
              <CssBaseline enableColorScheme />
              <Routing />
            </BrowserRouter>
          </BookingProvider>
        </EntitiesProvider>
      </LocalizationProvider>
    </ColorThemeProvider>
  );
};

export default App;
