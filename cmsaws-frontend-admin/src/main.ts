import { CommonModule } from "@angular/common";
import { Component, signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { bootstrapApplication } from "@angular/platform-browser";

type AdminRole = "ADMIN" | "EDITOR" | "READER";

type Role = {
  id: string;
  name: string;
};

type User = {
  id: string;
  name: string;
  email: string;
  roleId: string;
  roleName: string;
  statusDado: number;
};

type Category = {
  id: string;
  name: string;
};

type Article = {
  id: string;
  title: string;
  content: string;
  categoryId: string;
  categoryName: string;
  isHighlight: boolean;
};

@Component({
  selector: "app-root",
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <main class="layout">
      <header class="topbar">
        <div>
          <h1>NewsFlow Admin</h1>
          <p>Painel administrativo com CRUD e permissões por papel.</p>
        </div>

        <div class="role-box">
          <label for="role">Papel atual</label>
          <select id="role" [(ngModel)]="currentRole" (change)="onRoleChange()">
            <option value="ADMIN">ADMIN</option>
            <option value="EDITOR">EDITOR</option>
            <option value="READER">READER</option>
          </select>
        </div>
      </header>

      <p class="feedback" *ngIf="feedback()">{{ feedback() }}</p>

      <section class="grid">
        <article class="panel">
          <h2>Usuários</h2>
          <p class="muted">Somente ADMIN pode criar, editar e remover usuários.</p>

          <form class="form" *ngIf="canManageUsers()" (ngSubmit)="saveUser()">
            <input [(ngModel)]="userForm.name" name="userName" placeholder="Nome" required />
            <input [(ngModel)]="userForm.email" name="userEmail" type="email" placeholder="Email" required />
            <select [(ngModel)]="userForm.roleId" name="userRole" required>
              <option *ngFor="let role of roles()" [value]="role.id">{{ role.name }}</option>
            </select>
            <div class="actions">
              <button type="submit">{{ userEditId ? 'Atualizar' : 'Criar' }}</button>
              <button type="button" class="ghost" *ngIf="userEditId" (click)="resetUserForm()">Cancelar</button>
            </div>
          </form>

          <div class="list">
            <div class="item" *ngFor="let user of users()">
              <div>
                <strong>{{ user.name }}</strong>
                <p>{{ user.email }} • {{ user.roleName }}</p>
              </div>
              <div class="inline-actions" *ngIf="canManageUsers()">
                <button type="button" (click)="startEditUser(user)">Editar</button>
                <button type="button" class="danger" (click)="deleteUser(user.id)">Excluir</button>
              </div>
            </div>
          </div>
        </article>

        <article class="panel">
          <h2>Categorias</h2>
          <p class="muted">ADMIN e EDITOR possuem CRUD de categorias.</p>

          <form class="form" *ngIf="canEditCatalog()" (ngSubmit)="saveCategory()">
            <input [(ngModel)]="categoryForm.name" name="categoryName" placeholder="Nome da categoria" required />
            <div class="actions">
              <button type="submit">{{ categoryEditId ? 'Atualizar' : 'Criar' }}</button>
              <button type="button" class="ghost" *ngIf="categoryEditId" (click)="resetCategoryForm()">Cancelar</button>
            </div>
          </form>

          <div class="list">
            <div class="item" *ngFor="let category of categories()">
              <div>
                <strong>{{ category.name }}</strong>
              </div>
              <div class="inline-actions" *ngIf="canEditCatalog()">
                <button type="button" (click)="startEditCategory(category)">Editar</button>
                <button type="button" class="danger" (click)="deleteCategory(category.id)">Excluir</button>
              </div>
            </div>
          </div>
        </article>

        <article class="panel wide">
          <h2>Matérias</h2>
          <p class="muted">ADMIN e EDITOR possuem CRUD de matérias; READER apenas visualiza.</p>

          <form class="form" *ngIf="canEditCatalog()" (ngSubmit)="saveArticle()">
            <input [(ngModel)]="articleForm.title" name="articleTitle" placeholder="Título" required />
            <textarea [(ngModel)]="articleForm.content" name="articleContent" rows="4" placeholder="Conteúdo" required></textarea>
            <select [(ngModel)]="articleForm.categoryId" name="articleCategory" required>
              <option *ngFor="let category of categories()" [value]="category.id">{{ category.name }}</option>
            </select>
            <label class="checkbox">
              <input [(ngModel)]="articleForm.isHighlight" name="articleHighlight" type="checkbox" />
              Marcar como destaque
            </label>
            <div class="actions">
              <button type="submit">{{ articleEditId ? 'Atualizar' : 'Criar' }}</button>
              <button type="button" class="ghost" *ngIf="articleEditId" (click)="resetArticleForm()">Cancelar</button>
            </div>
          </form>

          <div class="list">
            <div class="item" *ngFor="let article of articles()">
              <div>
                <strong>{{ article.title }}</strong>
                <p>{{ article.categoryName }} • {{ article.isHighlight ? 'Destaque' : 'Comum' }}</p>
              </div>
              <div class="inline-actions" *ngIf="canEditCatalog()">
                <button type="button" (click)="startEditArticle(article)">Editar</button>
                <button type="button" class="danger" (click)="deleteArticle(article.id)">Excluir</button>
              </div>
            </div>
          </div>
        </article>
      </section>
    </main>
  `
})
class AppComponent {
  private readonly apiBase = (window as { __API_BASE__?: string }).__API_BASE__ || "http://localhost:8080";

  currentRole: AdminRole = "ADMIN";

  feedback = signal<string>("");
  roles = signal<Role[]>([]);
  users = signal<User[]>([]);
  categories = signal<Category[]>([]);
  articles = signal<Article[]>([]);

  userEditId: string | null = null;
  categoryEditId: string | null = null;
  articleEditId: string | null = null;

  userForm = { name: "", email: "", roleId: "" };
  categoryForm = { name: "" };
  articleForm = { title: "", content: "", categoryId: "", isHighlight: false };

  constructor() {
    this.loadAll();
  }

  canManageUsers() {
    return this.currentRole === "ADMIN";
  }

  canEditCatalog() {
    return this.currentRole === "ADMIN" || this.currentRole === "EDITOR";
  }

  onRoleChange() {
    this.feedback.set(`Permissões aplicadas para ${this.currentRole}.`);
  }

  async loadAll() {
    await Promise.all([this.loadRoles(), this.loadUsers(), this.loadCategories(), this.loadArticles()]);
  }

  async loadRoles() {
    this.roles.set(await this.request<Role[]>("/api/users/roles", "GET"));
    if (!this.userForm.roleId && this.roles().length > 0) {
      this.userForm.roleId = this.roles()[0].id;
    }
  }

  async loadUsers() {
    this.users.set(await this.request<User[]>("/api/users/users", "GET"));
  }

  async loadCategories() {
    this.categories.set(await this.request<Category[]>("/api/core/categories", "GET"));
    if (!this.articleForm.categoryId && this.categories().length > 0) {
      this.articleForm.categoryId = this.categories()[0].id;
    }
  }

  async loadArticles() {
    this.articles.set(await this.request<Article[]>("/api/core/articles", "GET"));
  }

  async saveUser() {
    if (!this.canManageUsers()) {
      this.feedback.set("Sem permissão para alterar usuários.");
      return;
    }

    const method = this.userEditId ? "PUT" : "POST";
    const path = this.userEditId ? `/api/users/users/${this.userEditId}` : "/api/users/users";

    await this.request(path, method, this.userForm);
    await this.loadUsers();
    this.resetUserForm();
    this.feedback.set("Usuário salvo com sucesso.");
  }

  startEditUser(user: User) {
    this.userEditId = user.id;
    this.userForm = { name: user.name, email: user.email, roleId: user.roleId };
  }

  resetUserForm() {
    this.userEditId = null;
    this.userForm = { name: "", email: "", roleId: this.roles()[0]?.id ?? "" };
  }

  async deleteUser(id: string) {
    if (!this.canManageUsers()) {
      this.feedback.set("Sem permissão para remover usuários.");
      return;
    }

    await this.request(`/api/users/users/${id}`, "DELETE");
    await this.loadUsers();
    this.feedback.set("Usuário removido com sucesso.");
  }

  async saveCategory() {
    if (!this.canEditCatalog()) {
      this.feedback.set("Sem permissão para alterar categorias.");
      return;
    }

    const method = this.categoryEditId ? "PUT" : "POST";
    const path = this.categoryEditId ? `/api/core/categories/${this.categoryEditId}` : "/api/core/categories";

    await this.request(path, method, this.categoryForm);
    await this.loadCategories();
    this.resetCategoryForm();
    this.feedback.set("Categoria salva com sucesso.");
  }

  startEditCategory(category: Category) {
    this.categoryEditId = category.id;
    this.categoryForm = { name: category.name };
  }

  resetCategoryForm() {
    this.categoryEditId = null;
    this.categoryForm = { name: "" };
  }

  async deleteCategory(id: string) {
    if (!this.canEditCatalog()) {
      this.feedback.set("Sem permissão para remover categorias.");
      return;
    }

    await this.request(`/api/core/categories/${id}`, "DELETE");
    await this.loadCategories();
    this.feedback.set("Categoria removida com sucesso.");
  }

  async saveArticle() {
    if (!this.canEditCatalog()) {
      this.feedback.set("Sem permissão para alterar matérias.");
      return;
    }

    const method = this.articleEditId ? "PUT" : "POST";
    const path = this.articleEditId ? `/api/core/articles/${this.articleEditId}` : "/api/core/articles";

    await this.request(path, method, this.articleForm);
    await this.loadArticles();
    this.resetArticleForm();
    this.feedback.set("Matéria salva com sucesso.");
  }

  startEditArticle(article: Article) {
    this.articleEditId = article.id;
    this.articleForm = {
      title: article.title,
      content: article.content,
      categoryId: article.categoryId,
      isHighlight: article.isHighlight
    };
  }

  resetArticleForm() {
    this.articleEditId = null;
    this.articleForm = {
      title: "",
      content: "",
      categoryId: this.categories()[0]?.id ?? "",
      isHighlight: false
    };
  }

  async deleteArticle(id: string) {
    if (!this.canEditCatalog()) {
      this.feedback.set("Sem permissão para remover matérias.");
      return;
    }

    await this.request(`/api/core/articles/${id}`, "DELETE");
    await this.loadArticles();
    this.feedback.set("Matéria removida com sucesso.");
  }

  private async request<T>(path: string, method: string, body?: unknown): Promise<T> {
    const response = await fetch(`${this.apiBase}${path}`, {
      method,
      headers: {
        "Content-Type": "application/json"
      },
      body: body ? JSON.stringify(body) : undefined
    });

    if (!response.ok) {
      this.feedback.set(`Falha na operação (${response.status}).`);
      throw new Error(`Request failed: ${response.status}`);
    }

    if (response.status === 204) {
      return undefined as T;
    }

    return (await response.json()) as T;
  }
}

bootstrapApplication(AppComponent).catch((error) => console.error(error));
