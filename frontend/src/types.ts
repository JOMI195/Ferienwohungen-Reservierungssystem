export interface Ferienwohnung {
    ferienwohnungs_id: number;
    ferienwohnungsname: string;
    mietpreis: number;
    zimmer: number;
    größe: number;
    straße: string;
    hausnummer: string;
    postleitzahl: string;
    ort: string;
    landname: string;
}

export interface Kunde {
    email: string;
    passwort: string;
    vorname: string;
    nachname: string;
    iban: string;
    newsletter: string;
    straße: string;
    hausnummer: string;
    postleitzahl: string;
    ort: string;
    landname: string;
}