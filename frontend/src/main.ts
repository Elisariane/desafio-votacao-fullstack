import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideNgxMask } from 'ngx-mask';
import { appConfig } from './app/app.config';
import { registerLocaleData } from '@angular/common';
import ptBr from '@angular/common/locales/pt';

registerLocaleData(ptBr);

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [...appConfig.providers!, provideNgxMask()]
});