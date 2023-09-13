import { ILoginForm } from '../models/ILoginForm';
import { IRegisterForm } from '../models/IRegisterForm';
import { API_URL } from '../utils/GlobalUtils';

export const login = (data: ILoginForm) => {
    const requestData = { appUsername: data.username, password: data.password };

    return new Promise((resolve, reject) => {
        fetch(API_URL.concat('login'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestData),
        })
            .then((response) => {
                if (response.ok) {
                    const headers = response.headers;
                    const jwt = headers.get('Authorization') || '';
                    if (localStorage.getItem('jwt')) {
                        localStorage.removeItem('jwt');
                    }
                    localStorage.setItem('jwt', JSON.stringify(jwt));//a voir si .stringify() résoud le pmessage d'erreur en console.
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

export const register = (data: IRegisterForm) => {
    const requestData = {
        appUsername: data.username,
        password: data.password,
        emailUser: data.emailUser,
        yearBirth: data.yearBirth,
        isMale: data.isMale,
        isEuropeanUnitMeasure: data.isEuropeanUnitMeasure,
        bodySize: data.bodySize,
        goalWeight: data.goalWeight,
    };

    return new Promise((resolve, reject) => {
        fetch(API_URL.concat('users/add'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestData),
        })
            .then((response) => {
                if (response.ok) {
                    const headers = response.headers;
                    const jwt = headers.get('Authorization') || '';
                    if (localStorage.getItem('jwt')) {
                        localStorage.removeItem('jwt');
                    }
                    localStorage.setItem('jwt',JSON.stringify(jwt));
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
