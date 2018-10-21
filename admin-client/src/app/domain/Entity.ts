export interface Entity {
    name: string;
    idName: string;
    properties: Property[];
}

export interface Property {
    name: string;
    value: string;
}
