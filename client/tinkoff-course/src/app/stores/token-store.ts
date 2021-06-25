export class TokenStore {
  private static readonly TOKEN_KEY = "access_token";

  public static setToken(token: string) {
    sessionStorage.setItem(this.TOKEN_KEY, token);
  }

  public static getToken() {
    return sessionStorage.getItem(this.TOKEN_KEY);
  }
}
