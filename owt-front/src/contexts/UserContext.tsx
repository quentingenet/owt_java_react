import { createContext, useContext, useState } from 'react';

interface IUserContext {
    jwt: string;
    setJwt: (jwt: string) => void;
    isUserLoggedIn: boolean;
    setIsUserLoggedIn: (isUserLoggedIn: boolean) => void;
    isEuropeanUnitMeasure: boolean;
    setIsEuropeanUnitMeasure: (isEuropeanUnitMeasure: boolean) => void;
}

export const UserContext = createContext<IUserContext>({
    jwt: '',
    setJwt: () => {
        ('');
    },
    isUserLoggedIn: false,
    setIsUserLoggedIn: () => {
        ('');
    },
    isEuropeanUnitMeasure: true,
    setIsEuropeanUnitMeasure: () => {
        ('');
    },
});

export function useUserContext() {
    return useContext(UserContext);
}

export function UserContextProvider({
    children,
}: {
    children: React.ReactNode;
}) {
    const [jwt, setJwt] = useState<string>('');
    const [isUserLoggedIn, setIsUserLoggedIn] = useState<boolean>(false);
    const [isEuropeanUnitMeasure, setIsEuropeanUnitMeasure] =
        useState<boolean>(true);

    const value: IUserContext = {
        jwt: jwt,
        setJwt: setJwt,
        isUserLoggedIn: isUserLoggedIn,
        setIsUserLoggedIn: setIsUserLoggedIn,
        isEuropeanUnitMeasure: isEuropeanUnitMeasure,
        setIsEuropeanUnitMeasure: setIsEuropeanUnitMeasure,
    };

    return (
        <UserContext.Provider value={value}>{children}</UserContext.Provider>
    );
}
