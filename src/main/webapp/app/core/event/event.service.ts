import {EventEmitter, Injectable, Output} from '@angular/core';

@Injectable({ providedIn: 'root' })
export class EventService {

  @Output() addItemToCart: EventEmitter<any> = new EventEmitter();
  @Output() loginCompleted: EventEmitter<any> = new EventEmitter();
  @Output() cartEmptied: EventEmitter<any> = new EventEmitter();
}
