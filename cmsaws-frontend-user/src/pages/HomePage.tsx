import type { FormEvent } from "react";
import { useEffect, useMemo, useState } from "react";
import { api } from "../services/api";

type Article = {
  id: string;
  title: string;
  content: string;
  categoryId: string;
  categoryName: string;
  isHighlight: boolean;
};

type Category = {
  id: string;
  name: string;
};

export function HomePage() {
  const [articles, setArticles] = useState<Article[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [activeTab, setActiveTab] = useState<"home" | "auth" | "contact">("home");

  const [loginEmail, setLoginEmail] = useState("");
  const [loginResult, setLoginResult] = useState<string | null>(null);
  const [loginLoading, setLoginLoading] = useState(false);

  const [registerName, setRegisterName] = useState("");
  const [registerEmail, setRegisterEmail] = useState("");
  const [registerResult, setRegisterResult] = useState<string | null>(null);
  const [registerLoading, setRegisterLoading] = useState(false);

  const [departments, setDepartments] = useState<Category[]>([]);
  const [departmentId, setDepartmentId] = useState("");
  const [contactName, setContactName] = useState("");
  const [contactEmail, setContactEmail] = useState("");
  const [contactMessage, setContactMessage] = useState("");
  const [contactResult, setContactResult] = useState<string | null>(null);
  const [contactLoading, setContactLoading] = useState(false);

  useEffect(() => {
    let active = true;

    async function loadData() {
      setLoading(true);
      setError(null);

      try {
        const [articlesResponse, categoriesResponse] = await Promise.all([
          api.get<Article[]>("/api/core/articles"),
          api.get<Category[]>("/api/core/categories")
        ]);

        if (!active) {
          return;
        }

        setArticles(Array.isArray(articlesResponse.data) ? articlesResponse.data : []);
        setCategories(Array.isArray(categoriesResponse.data) ? categoriesResponse.data : []);
      } catch {
        if (active) {
          setError("Nao foi possivel carregar os dados da home no momento.");
        }
      } finally {
        if (active) {
          setLoading(false);
        }
      }
    }

    loadData();

    return () => {
      active = false;
    };
  }, []);

  useEffect(() => {
    let active = true;

    async function loadDepartments() {
      try {
        const response = await api.get<Category[]>("/api/contacts/departments");
        if (!active) {
          return;
        }
        const data = Array.isArray(response.data) ? response.data : [];
        setDepartments(data);
        if (data.length > 0) {
          setDepartmentId(data[0].id);
        }
      } catch {
        if (active) {
          setDepartments([]);
        }
      }
    }

    loadDepartments();

    return () => {
      active = false;
    };
  }, []);

  const highlights = useMemo(() => articles.filter((article) => article.isHighlight).slice(0, 3), [articles]);

  async function handleLogin(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setLoginLoading(true);
    setLoginResult(null);

    try {
      const response = await api.post<{ name: string; roleName: string }>("/api/users/auth/login", { email: loginEmail });
      setLoginResult(`Login realizado: ${response.data.name} (${response.data.roleName})`);
    } catch {
      setLoginResult("Falha no login. Verifique o email cadastrado.");
    } finally {
      setLoginLoading(false);
    }
  }

  async function handleRegister(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setRegisterLoading(true);
    setRegisterResult(null);

    try {
      const rolesResponse = await api.get<Array<{ id: string; name: string }>>("/api/users/roles");
      const readerRole = rolesResponse.data.find((role) => role.name.toUpperCase() === "READER");

      if (!readerRole) {
        setRegisterResult("Role READER nao encontrada no backend.");
        return;
      }

      await api.post("/api/users/users", {
        name: registerName,
        email: registerEmail,
        roleId: readerRole.id
      });

      setRegisterResult("Cadastro criado com sucesso.");
      setRegisterName("");
      setRegisterEmail("");
    } catch {
      setRegisterResult("Falha ao criar cadastro.");
    } finally {
      setRegisterLoading(false);
    }
  }

  async function handleContact(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setContactLoading(true);
    setContactResult(null);

    try {
      await api.post("/api/contacts/messages", {
        departmentId,
        name: contactName,
        email: contactEmail,
        message: contactMessage
      });

      setContactResult("Mensagem enviada com sucesso.");
      setContactName("");
      setContactEmail("");
      setContactMessage("");
    } catch {
      setContactResult("Falha ao enviar mensagem de contato.");
    } finally {
      setContactLoading(false);
    }
  }

  return (
    <main style={{ fontFamily: "Segoe UI, sans-serif", padding: "2rem", maxWidth: "1100px", margin: "0 auto" }}>
      <header style={{ marginBottom: "1.5rem" }}>
        <h1 style={{ margin: "0 0 0.5rem" }}>NewsFlow User Portal</h1>
        <p style={{ margin: 0, color: "#4b5563" }}>Portal de leitura com categorias dinamicas e destaques.</p>
        <nav style={{ display: "flex", gap: "0.5rem", marginTop: "1rem", flexWrap: "wrap" }}>
          <button type="button" onClick={() => setActiveTab("home")}>Home</button>
          <button type="button" onClick={() => setActiveTab("auth")}>Autenticacao</button>
          <button type="button" onClick={() => setActiveTab("contact")}>Contato</button>
        </nav>
      </header>

      {loading && <p>Carregando conteudo...</p>}

      {!loading && error && (
        <section aria-label="estado-erro">
          <p style={{ color: "#b91c1c" }}>{error}</p>
        </section>
      )}

      {!loading && !error && activeTab === "home" && (
        <>
          <section aria-label="menu-categorias" style={{ marginBottom: "1.5rem" }}>
            <h2 style={{ marginBottom: "0.75rem" }}>Categorias</h2>
            {categories.length === 0 ? (
              <p>Nenhuma categoria disponivel.</p>
            ) : (
              <div style={{ display: "flex", flexWrap: "wrap", gap: "0.5rem" }}>
                {categories.map((category) => (
                  <span
                    key={category.id}
                    style={{
                      border: "1px solid #d1d5db",
                      borderRadius: "9999px",
                      padding: "0.3rem 0.8rem",
                      fontSize: "0.9rem"
                    }}
                  >
                    {category.name}
                  </span>
                ))}
              </div>
            )}
          </section>

          <section aria-label="destaques" style={{ marginBottom: "1.5rem" }}>
            <h2 style={{ marginBottom: "0.75rem" }}>Destaques</h2>
            {highlights.length === 0 ? (
              <p>Nenhum destaque disponivel.</p>
            ) : (
              <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))", gap: "0.75rem" }}>
                {highlights.map((article) => (
                  <article key={article.id} style={{ border: "1px solid #e5e7eb", borderRadius: "10px", padding: "0.9rem" }}>
                    <h3 style={{ marginTop: 0 }}>{article.title}</h3>
                    <p style={{ marginBottom: 0, color: "#374151" }}>{article.content.slice(0, 120)}</p>
                  </article>
                ))}
              </div>
            )}
          </section>

          <section aria-label="lista-materias">
            <h2 style={{ marginBottom: "0.75rem" }}>Ultimas materias</h2>
            {articles.length === 0 ? (
              <p>Nenhuma materia publicada ate o momento.</p>
            ) : (
              <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(260px, 1fr))", gap: "0.75rem" }}>
                {articles.map((article) => (
                  <article key={article.id} style={{ border: "1px solid #e5e7eb", borderRadius: "10px", padding: "0.9rem" }}>
                    <div style={{ fontSize: "0.85rem", color: "#6b7280" }}>{article.categoryName}</div>
                    <h3 style={{ marginTop: "0.4rem", marginBottom: "0.4rem" }}>{article.title}</h3>
                    <p style={{ marginBottom: 0, color: "#374151" }}>{article.content.slice(0, 180)}</p>
                  </article>
                ))}
              </div>
            )}
          </section>
        </>
      )}

      {activeTab === "auth" && (
        <section aria-label="autenticacao" style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(280px, 1fr))", gap: "1rem" }}>
          <form onSubmit={handleLogin} style={{ border: "1px solid #e5e7eb", borderRadius: "10px", padding: "1rem" }}>
            <h2 style={{ marginTop: 0 }}>Login</h2>
            <label htmlFor="login-email">Email</label>
            <input
              id="login-email"
              type="email"
              value={loginEmail}
              onChange={(event) => setLoginEmail(event.target.value)}
              required
              style={{ width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" }}
            />
            <button type="submit" disabled={loginLoading}>{loginLoading ? "Entrando..." : "Entrar"}</button>
            {loginResult && <p style={{ marginTop: "0.75rem" }}>{loginResult}</p>}
          </form>

          <form onSubmit={handleRegister} style={{ border: "1px solid #e5e7eb", borderRadius: "10px", padding: "1rem" }}>
            <h2 style={{ marginTop: 0 }}>Cadastro</h2>
            <label htmlFor="register-name">Nome</label>
            <input
              id="register-name"
              type="text"
              value={registerName}
              onChange={(event) => setRegisterName(event.target.value)}
              required
              style={{ width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" }}
            />
            <label htmlFor="register-email">Email</label>
            <input
              id="register-email"
              type="email"
              value={registerEmail}
              onChange={(event) => setRegisterEmail(event.target.value)}
              required
              style={{ width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" }}
            />
            <button type="submit" disabled={registerLoading}>{registerLoading ? "Salvando..." : "Cadastrar"}</button>
            {registerResult && <p style={{ marginTop: "0.75rem" }}>{registerResult}</p>}
          </form>
        </section>
      )}

      {activeTab === "contact" && (
        <section aria-label="contato" style={{ maxWidth: "680px" }}>
          <form onSubmit={handleContact} style={{ border: "1px solid #e5e7eb", borderRadius: "10px", padding: "1rem" }}>
            <h2 style={{ marginTop: 0 }}>Fale Conosco</h2>
            <label htmlFor="department">Departamento</label>
            <select
              id="department"
              value={departmentId}
              onChange={(event) => setDepartmentId(event.target.value)}
              required
              style={{ width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" }}
            >
              {departments.map((department) => (
                <option key={department.id} value={department.id}>{department.name}</option>
              ))}
            </select>

            <label htmlFor="contact-name">Nome</label>
            <input
              id="contact-name"
              type="text"
              value={contactName}
              onChange={(event) => setContactName(event.target.value)}
              required
              style={{ width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" }}
            />

            <label htmlFor="contact-email">Email</label>
            <input
              id="contact-email"
              type="email"
              value={contactEmail}
              onChange={(event) => setContactEmail(event.target.value)}
              required
              style={{ width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" }}
            />

            <label htmlFor="contact-message">Mensagem</label>
            <textarea
              id="contact-message"
              value={contactMessage}
              onChange={(event) => setContactMessage(event.target.value)}
              required
              rows={5}
              style={{ width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" }}
            />

            <button type="submit" disabled={contactLoading || !departmentId}>{contactLoading ? "Enviando..." : "Enviar"}</button>
            {contactResult && <p style={{ marginTop: "0.75rem" }}>{contactResult}</p>}
          </form>
        </section>
      )}
    </main>
  );
}