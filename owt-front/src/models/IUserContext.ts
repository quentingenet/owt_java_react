export interface IUserContext {
    jwt: string;
    setJwt:(jwt: string) => void;
    isUserLoggedIn: boolean;
    setIsUserLoggedIn:(isUserLoggedIn: boolean) => void;
}