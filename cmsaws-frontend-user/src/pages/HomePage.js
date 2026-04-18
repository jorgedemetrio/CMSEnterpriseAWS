import { jsx as _jsx, jsxs as _jsxs, Fragment as _Fragment } from "react/jsx-runtime";
import { useEffect, useMemo, useState } from "react";
import { api } from "../services/api";
export function HomePage() {
    const [articles, setArticles] = useState([]);
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    useEffect(() => {
        let active = true;
        async function loadData() {
            setLoading(true);
            setError(null);
            try {
                const [articlesResponse, categoriesResponse] = await Promise.all([
                    api.get("/api/core/articles"),
                    api.get("/api/core/categories")
                ]);
                if (!active) {
                    return;
                }
                setArticles(Array.isArray(articlesResponse.data) ? articlesResponse.data : []);
                setCategories(Array.isArray(categoriesResponse.data) ? categoriesResponse.data : []);
            }
            catch {
                if (active) {
                    setError("Nao foi possivel carregar os dados da home no momento.");
                }
            }
            finally {
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
    return (_jsxs("main", { style: { fontFamily: "Segoe UI, sans-serif", padding: "2rem", maxWidth: "1100px", margin: "0 auto" }, children: [_jsxs("header", { style: { marginBottom: "1.5rem" }, children: [_jsx("h1", { style: { margin: "0 0 0.5rem" }, children: "NewsFlow User Portal" }), _jsx("p", { style: { margin: 0, color: "#4b5563" }, children: "Portal de leitura com categorias dinamicas e destaques." })] }), loading && _jsx("p", { children: "Carregando conteudo..." }), !loading && error && (_jsx("section", { "aria-label": "estado-erro", children: _jsx("p", { style: { color: "#b91c1c" }, children: error }) })), !loading && !error && (_jsxs(_Fragment, { children: [_jsxs("section", { "aria-label": "menu-categorias", style: { marginBottom: "1.5rem" }, children: [_jsx("h2", { style: { marginBottom: "0.75rem" }, children: "Categorias" }), categories.length === 0 ? (_jsx("p", { children: "Nenhuma categoria disponivel." })) : (_jsx("div", { style: { display: "flex", flexWrap: "wrap", gap: "0.5rem" }, children: categories.map((category) => (_jsx("span", { style: {
                                        border: "1px solid #d1d5db",
                                        borderRadius: "9999px",
                                        padding: "0.3rem 0.8rem",
                                        fontSize: "0.9rem"
                                    }, children: category.name }, category.id))) }))] }), _jsxs("section", { "aria-label": "destaques", style: { marginBottom: "1.5rem" }, children: [_jsx("h2", { style: { marginBottom: "0.75rem" }, children: "Destaques" }), highlights.length === 0 ? (_jsx("p", { children: "Nenhum destaque disponivel." })) : (_jsx("div", { style: { display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))", gap: "0.75rem" }, children: highlights.map((article) => (_jsxs("article", { style: { border: "1px solid #e5e7eb", borderRadius: "10px", padding: "0.9rem" }, children: [_jsx("h3", { style: { marginTop: 0 }, children: article.title }), _jsx("p", { style: { marginBottom: 0, color: "#374151" }, children: article.content.slice(0, 120) })] }, article.id))) }))] }), _jsxs("section", { "aria-label": "lista-materias", children: [_jsx("h2", { style: { marginBottom: "0.75rem" }, children: "Ultimas materias" }), articles.length === 0 ? (_jsx("p", { children: "Nenhuma materia publicada ate o momento." })) : (_jsx("div", { style: { display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(260px, 1fr))", gap: "0.75rem" }, children: articles.map((article) => (_jsxs("article", { style: { border: "1px solid #e5e7eb", borderRadius: "10px", padding: "0.9rem" }, children: [_jsx("div", { style: { fontSize: "0.85rem", color: "#6b7280" }, children: article.categoryName }), _jsx("h3", { style: { marginTop: "0.4rem", marginBottom: "0.4rem" }, children: article.title }), _jsx("p", { style: { marginBottom: 0, color: "#374151" }, children: article.content.slice(0, 180) })] }, article.id))) }))] })] }))] }));
}
