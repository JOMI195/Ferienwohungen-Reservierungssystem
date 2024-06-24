import { useAusstattungContext } from './ausstattungContext';
import { useBesitztContext } from './besitztContext';
import { useBildContext } from './bildContext';
import { useBuchungContext } from './buchungContext';
import { useFerienwohnungContext } from './ferienwohnungContext';
import { useKundeContext } from './kundeContext';
import { useLandContext } from './landContext';

export const useEntitiesContext = () => {
    const ferienwohnungContext = useFerienwohnungContext();
    const kundeContext = useKundeContext();
    const bildContext = useBildContext();
    const ausstattungContext = useAusstattungContext();
    const landContext = useLandContext();
    const buchungContext = useBuchungContext();
    const besitztContext = useBesitztContext();

    return {
        ...ferienwohnungContext,
        ...kundeContext,
        ...bildContext,
        ...ausstattungContext,
        ...landContext,
        ...buchungContext,
        ...besitztContext,
    };
};
