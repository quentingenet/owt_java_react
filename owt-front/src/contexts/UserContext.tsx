import { createContext, useContext, useState } from 'react';

interface IUserContext {
	jwt: string;
	setJwt: (jwt: string) => void;
	isUserLoggedIn: boolean;
	setIsUserLoggedIn: (isUserLoggedIn: boolean) => void;
}
export const UserContext = createContext<IUserContext>({
	jwt: '',
	setJwt: () => {},
	isUserLoggedIn: false,
	setIsUserLoggedIn: () => {},
});

export function useUserContext() {
	return useContext(UserContext);
}

export function UserContextProvider({ children }: { children: React.ReactNode }) {
	const [jwt, setJwt] = useState<string>('');
	const [isUserLoggedIn, setIsUserLoggedIn] = useState<boolean>(false);

	const value: IUserContext = {
		jwt: jwt,
		setJwt: setJwt,
		isUserLoggedIn: isUserLoggedIn,
		setIsUserLoggedIn: setIsUserLoggedIn,
	};

	return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
}
