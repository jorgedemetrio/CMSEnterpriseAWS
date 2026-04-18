import { bootstrapApplication } from "@angular/platform-browser";
import { Component } from "@angular/core";

@Component({
  selector: "app-root",
  standalone: true,
  template: `<main style="padding:2rem"><h1>NewsFlow Admin</h1><p>Scaffold inicial do painel administrativo.</p></main>`
})
class AppComponent {}

bootstrapApplication(AppComponent).catch((error) => console.error(error));