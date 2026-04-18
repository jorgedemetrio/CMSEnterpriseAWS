import { useEffect, useMemo, useState } from "react";
import { api } from "../services/api";

type Article = {
  id: string;
  title: string;
  content: string;
  categoryId: string;
  categoryName: string;
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

  const highlights = useMemo(() => articles.slice(0, 3), [articles]);

  return (
    <main style={{ fontFamily: "Segoe UI, sans-serif", padding: "2rem", maxWidth: "1100px", margin: "0 auto" }}>
      <header style={{ marginBottom: "1.5rem" }}>
        <h1 style={{ margin: "0 0 0.5rem" }}>NewsFlow User Portal</h1>
        <p style={{ margin: 0, color: "#4b5563" }}>Portal de leitura com categorias dinamicas e destaques.</p>
      </header>

      {loading && <p>Carregando conteudo...</p>}

      {!loading && error && (
        <section aria-label="estado-erro">
          <p style={{ color: "#b91c1c" }}>{error}</p>
        </section>
      )}

      {!loading && !error && (
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
    </main>
  );
}