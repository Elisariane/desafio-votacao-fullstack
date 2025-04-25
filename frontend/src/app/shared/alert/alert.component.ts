import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-alert',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './alert.component.html',
})
export class AlertComponent implements OnChanges {
  @Input() message: string | null = null;
  @Input() type: 'success' | 'danger' = 'success';
  @Input() duration = 5000;    // duração em ms

  visible = false;

  ngOnChanges(changes: SimpleChanges) {
    if (changes['message'] && this.message) {
      this.show();
    }
  }

  private show() {
    this.visible = true;
    setTimeout(() => this.visible = false, this.duration);
  }
}