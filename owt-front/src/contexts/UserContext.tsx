import { createContext, useContext, useState } from 'react';

interface IUserContext {
	jwt: string;
	setJwt: (jwt: string) => void;
	isUserLoggedIn: boolean;
	setIsUserLoggedIn: (isUserLoggedIn: boolean) => void;
	isRegistered: boolean;
	setIsRegistered: (isRegistered: boolean)=> void;
}
export const UserContext = createContext<IUserContext>({
	jwt: '',
	setJwt: () => {},
	isUserLoggedIn: false,
	setIsUserLoggedIn: () => {},
	isRegistered: false,
	setIsRegistered: ()=>{}
});

export function useUserContext() {
	return useContext(UserContext);
}

export function UserContextProvider({ children }: { children: React.ReactNode }) {
	const [jwt, setJwt] = useState<string>('');
	const [isUserLoggedIn, setIsUserLoggedIn] = useState<boolean>(false);
	const [isRegistered, setIsRegistered] = useState<boolean>(false);

	const value: IUserContext = {
		jwt: jwt,
		setJwt: setJwt,
		isUserLoggedIn: isUserLoggedIn,
		setIsUserLoggedIn: setIsUserLoggedIn,
		isRegistered: isRegistered,
		setIsRegistered: setIsRegistered
	};

	return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
}
