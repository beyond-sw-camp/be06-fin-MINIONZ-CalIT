export const personaList = [
    {
        id: 1,
        src: () => require('@/assets/icon/persona/user1.svg')
    },
    {
        id: 2,
        src: () => require('@/assets/icon/persona/user2.svg')
    },
    {
        id: 3,
        src: () => require('@/assets/icon/persona/user3.svg')
    },
    {
        id: 4,
        src: () => require('@/assets/icon/persona/user4.svg')
    },
    {
        id: 5,
        src: () => require('@/assets/icon/persona/user5.svg')
    },
    {
        id: 6,
        src: () => require('@/assets/icon/persona/user6.svg')
    },
    {
        id: 7,
        src: () => require('@/assets/icon/persona/space1.svg')
    },
    {
        id: 8,
        src: () => require('@/assets/icon/persona/space2.svg')
    },
    {
        id: 9,
        src: () => require('@/assets/icon/persona/space3.svg')
    },
    {
        id: 10,
        src: () => require('@/assets/icon/persona/space4.svg')
    },
    {
        id: 11,
        src: () => require('@/assets/icon/persona/space5.svg')
    },
    {
        id: 12,
        src: () => require('@/assets/icon/persona/space6.svg')
    }
]

export function setPersona(id) {
    return personaList.find(persona => persona.id === id).src();
}