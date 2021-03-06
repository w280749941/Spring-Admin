import { NgModule, ModuleWithProviders, InjectionToken  } from '@angular/core';
import { ModelService } from './model.service';


@NgModule()
export class ServicesModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: ServicesModule,
      providers: [
        ModelService,
      ]
    };
  }
}
