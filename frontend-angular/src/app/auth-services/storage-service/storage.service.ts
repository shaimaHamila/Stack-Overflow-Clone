import { Injectable } from '@angular/core';

const TOKEN = 'c_token';
const USER = 'c_user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  // static hasToken(): boolean {
  //   if (this.getToken() === null) {
  //     return false;
  //   }
  //   return true;
  // }
  static hasToken(): boolean {
    // Check if localStorage is defined before using it
    if (typeof localStorage !== 'undefined' && localStorage.getItem(TOKEN) == null) {
      return false;
    }
    return true;
  }
  public saveUser(user: any) {
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem(USER);
      window.localStorage.setItem(USER, JSON.stringify(user));
    }
  };
  // public saveUser(user: any) {
  //   window.localStorage.removeItem(USER);
  //   window.localStorage.setItem(USER, JSON.stringify(user));
  // };
  static getUser(): any {
    if (typeof window !== 'undefined') {
      try {
        const userString = window.localStorage.getItem(USER);
        return userString ? JSON.parse(userString) : null;
      } catch (error) {
        console.error('Error parsing user data:', error);
        return null;
      }
    }
    return null;
  }

  static getUserId(): string {
    const user = this.getUser();
    if (user == null || typeof user !== 'object' || !('userId' in user)) {
      return "";
    }
    return user.userId;
  }
  // static getUser(): any {
  //   return JSON.parse(localStorage.getItem(USER)!);
  // }
  public saveToken(token: string) {
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem(TOKEN);
      window.localStorage.setItem(TOKEN, token);
    }
  }
  // public saveToken(token: string): void {
  //   window.localStorage.removeItem(TOKEN);
  //   window.localStorage.setItem(TOKEN, token);
  // }
  static getToken(): string | null {
    return typeof window !== 'undefined' ? window.localStorage.getItem(TOKEN) : null;
  }
  // static getToken(): any {
  //   return localStorage.getItem(TOKEN);
  // }
  static isUserLoggedIn() {
    if (this.getToken() == null) {
      return false;
    }
    return true;
  }

  static logout() {
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem(TOKEN);
      window.localStorage.removeItem(USER);
    }
  }
  // static logout() {
  //   window.localStorage.removeItem(TOKEN);
  //   window.localStorage.removeItem(USER);
  // }
}
