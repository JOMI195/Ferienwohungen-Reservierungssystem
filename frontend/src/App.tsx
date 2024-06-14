import { BrowserRouter } from 'react-router-dom';
import { CssBaseline, ThemeProvider } from '@mui/material';
import Routing from './routes/routing';
import useColorTheme from './common/hooks/useColorMode';
import { EntitiesProvider } from './context/entitiesContext';

const App = () => {
  const colorTheme = useColorTheme();

  return (
    <EntitiesProvider>
      <ThemeProvider theme={colorTheme}>
        <BrowserRouter>
          <CssBaseline enableColorScheme />
          <Routing />
        </BrowserRouter>
      </ThemeProvider>
    </EntitiesProvider>
  );
};

export default App;
