import { BrowserRouter } from 'react-router-dom';
import { CssBaseline, ThemeProvider } from '@mui/material';
import Routing from './routes/routing';
import useColorTheme from './common/hooks/useColorMode';
import { EntitiesProvider } from './context/entities/entitiesProvider';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { BookingProvider } from './context/booking/bookingContext';

const App = () => {
  const colorTheme = useColorTheme();

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <EntitiesProvider>
        <BookingProvider>
          <ThemeProvider theme={colorTheme}>
            <BrowserRouter>
              <CssBaseline enableColorScheme />
              <Routing />
            </BrowserRouter>
          </ThemeProvider>
        </BookingProvider>
      </EntitiesProvider>
    </LocalizationProvider>
  );
};

export default App;
