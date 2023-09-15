export const passwordAtLeast4= /^.{4,}$/;
export const passwordWithNumber = /^(?=.*\d).+$/;
export const passwordWithLetter = /^(?=.*[a-zA-Z]).+$/;
export const emailValidator =
/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/