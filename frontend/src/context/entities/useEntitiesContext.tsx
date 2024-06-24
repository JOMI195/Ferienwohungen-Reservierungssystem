import { useAusstattungContext } from './ausstattungContext';
import { useBesitztContext } from './besitztContext';
import { useBildContext } from './bildContext';
import { useBuchungContext } from './buchungContext';
import { useFerienwohnungContext } from './ferienwohnungContext';
import { useKundeContext } from './kundeContext';
import { useLandContext } from './landContext';
import { useLiegtInDerNaeheVonContext } from './liegtInDerNaeheVonContext';
import { useTouristenattraktionContext } from './touristenattraktionContext';

export const useEntitiesContext = () => {
    const ferienwohnungContext = useFerienwohnungContext();
    const kundeContext = useKundeContext();
    const bildContext = useBildContext();
    const ausstattungContext = useAusstattungContext();
    const landContext = useLandContext();
    const buchungContext = useBuchungContext();
    const besitztContext = useBesitztContext();
    const touristenattraktionContext = useTouristenattraktionContext();
    const liegtInDerNaeheVonContext = useLiegtInDerNaeheVonContext();

    return {
        ...ferienwohnungContext,
        ...kundeContext,
        ...bildContext,
        ...ausstattungContext,
        ...landContext,
        ...buchungContext,
        ...besitztContext,
        ...touristenattraktionContext,
        ...liegtInDerNaeheVonContext,
    };
};
