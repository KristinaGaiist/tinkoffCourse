import {Injectable} from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from "rxjs/operators";
import {MatSnackBar} from '@angular/material/snack-bar';
import {BackendError} from 'src/app/models/error';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private _snackBar: MatSnackBar) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
    .pipe(tap(
        event => event instanceof HttpResponse ? 'succeeded' : '',
        (err: HttpErrorResponse) => {
          const backendError: BackendError = err.error;
          console.log(backendError);
          this._snackBar.open(backendError.message, "Close");
        }
    ))
  }
}
