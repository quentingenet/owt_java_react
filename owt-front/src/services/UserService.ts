import { ILoginForm } from '../models/ILoginForm';
import { IRegisterForm } from '../models/IRegisterForm';
import { API_URL } from '../utils/GlobalUtils';

export const login = (data: ILoginForm) => {
    const requestDataLogin = {
        appUsername: data.username,
        password: data.password,
    };
    return new Promise((resolve, reject) => {
        fetch(API_URL.concat('login'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestDataLogin),
        })
            .then((response) => {
                if (response.ok) {
                    const headers = response.headers;
                    const jwt = headers.get('Authorization') || '';
                    if (localStorage.getItem('jwt')) {
                        localStorage.removeItem('jwt');
                    }
                    localStorage.setItem('jwt', JSON.stringify(jwt));
                    resolve(response.json());
                } else {
                    throw new Error("Erreur lors de la requête à l'API");
                }
            })
            .catch((error) => {
                reject(new Error("Erreur lors de l'appel à l'API :" + error));
            });
    });
};

export const register = (dataRegister: IRegisterForm) => {
    const requestDataRegister = {
        appUsername: dataRegister.username,
        password: dataRegister.password,
        emailUser: dataRegister.emailUser,
        yearBirth: dataRegister.yearBirth,
        isMale: dataRegister.isMale,
        isEuropeanUnitMeasure: dataRegister.isEuropeanUnitMeasure,
        bodySize: dataRegister.bodySize,
        goalWeight: dataRegister.goalWeight,
    };

    return new Promise((resolve, reject) => {
        fetch(API_URL.concat('users/add'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestDataRegister),
        })
            .then((response) => {
                if (response.ok) {
                    const headers = response.headers;
                    const jwt = headers.get('Authorization') || '';
                    if (localStorage.getItem('jwt')) {
                        localStorage.removeItem('jwt');
                    }
                    localStorage.setItem('jwt', JSON.stringify(jwt));
                    resolve(response.json());
                } else {
                    throw new Error("Erreur lors de la requête à l'API");
                }
            })
            .catch((error) => {
                reject(new Error("Erreur lors de l'appel à l'API :" + error));
            });
    });
};

export const resetAndChangePassword = (emailResetPassword: string) => {
    const emailUser = emailResetPassword;

    return fetch(API_URL.concat('resetPassword'), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(emailUser),
    });
};
