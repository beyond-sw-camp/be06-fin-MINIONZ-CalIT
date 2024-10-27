export const labelColorPalette= [
    {
        id: 0,
        backgroundColor: "rgba(234, 179, 8, 0.2)",
        color: "rgba(234, 179, 8)"
    },
    {
        id: 1,
        backgroundColor: 'rgba(236, 72, 153, 0.2)',
        color: 'rgba(236, 72, 153)'
    },
    {
        id: 2,
        backgroundColor: 'rgba(168, 85, 247, 0.2)',
        color: 'rgba(168, 85, 247)'
    },
    {
        id: 3,
        backgroundColor: 'rgba(8, 234, 179, 0.2)',
        color: 'rgba(8, 234, 179)'
    },
    {
        id: 4,
        backgroundColor: 'rgba(34, 197, 94, 0.2)',
        color: 'rgba(34, 197, 94)'
    },
    {
        id: 5,
        backgroundColor: "rgba(8,181,234,0.2)",
        color: "rgba(8,181,234)"
    },
];

export function getLabelColors() {
    return labelColorPalette.map(color => color.color);
}