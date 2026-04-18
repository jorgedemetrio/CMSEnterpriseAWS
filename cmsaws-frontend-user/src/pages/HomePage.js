import { jsx as _jsx, jsxs as _jsxs, Fragment as _Fragment } from "react/jsx-runtime";
import { useEffect, useMemo, useState } from "react";
import { api } from "../services/api";
export function HomePage() {
    const [articles, setArticles] = useState([]);
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [activeTab, setActiveTab] = useState("home");
    const [loginEmail, setLoginEmail] = useState("");
    const [loginResult, setLoginResult] = useState(null);
    const [loginLoading, setLoginLoading] = useState(false);
    const [registerName, setRegisterName] = useState("");
    const [registerEmail, setRegisterEmail] = useState("");
    const [registerResult, setRegisterResult] = useState(null);
    const [registerLoading, setRegisterLoading] = useState(false);
    const [departments, setDepartments] = useState([]);
    const [departmentId, setDepartmentId] = useState("");
    const [contactName, setContactName] = useState("");
    const [contactEmail, setContactEmail] = useState("");
    const [contactMessage, setContactMessage] = useState("");
    const [contactResult, setContactResult] = useState(null);
    const [contactLoading, setContactLoading] = useState(false);
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
    useEffect(() => {
        let active = true;
        async function loadDepartments() {
            try {
                const response = await api.get("/api/contacts/departments");
                if (!active) {
                    return;
                }
                const data = Array.isArray(response.data) ? response.data : [];
                setDepartments(data);
                if (data.length > 0) {
                    setDepartmentId(data[0].id);
                }
            }
            catch {
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
    async function handleLogin(event) {
        event.preventDefault();
        setLoginLoading(true);
        setLoginResult(null);
        try {
            const response = await api.post("/api/users/auth/login", { email: loginEmail });
            setLoginResult(`Login realizado: ${response.data.name} (${response.data.roleName})`);
        }
        catch {
            setLoginResult("Falha no login. Verifique o email cadastrado.");
        }
        finally {
            setLoginLoading(false);
        }
    }
    async function handleRegister(event) {
        event.preventDefault();
        setRegisterLoading(true);
        setRegisterResult(null);
        try {
            const rolesResponse = await api.get("/api/users/roles");
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
        }
        catch {
            setRegisterResult("Falha ao criar cadastro.");
        }
        finally {
            setRegisterLoading(false);
        }
    }
    async function handleContact(event) {
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
        }
        catch {
            setContactResult("Falha ao enviar mensagem de contato.");
        }
        finally {
            setContactLoading(false);
        }
    }
    return (_jsxs("main", { style: { fontFamily: "Segoe UI, sans-serif", padding: "2rem", maxWidth: "1100px", margin: "0 auto" }, children: [_jsxs("header", { style: { marginBottom: "1.5rem" }, children: [_jsx("h1", { style: { margin: "0 0 0.5rem" }, children: "NewsFlow User Portal" }), _jsx("p", { style: { margin: 0, color: "#4b5563" }, children: "Portal de leitura com categorias dinamicas e destaques." }), _jsxs("nav", { style: { display: "flex", gap: "0.5rem", marginTop: "1rem", flexWrap: "wrap" }, children: [_jsx("button", { type: "button", onClick: () => setActiveTab("home"), children: "Home" }), _jsx("button", { type: "button", onClick: () => setActiveTab("auth"), children: "Autenticacao" }), _jsx("button", { type: "button", onClick: () => setActiveTab("contact"), children: "Contato" })] })] }), loading && _jsx("p", { children: "Carregando conteudo..." }), !loading && error && (_jsx("section", { "aria-label": "estado-erro", children: _jsx("p", { style: { color: "#b91c1c" }, children: error }) })), !loading && !error && activeTab === "home" && (_jsxs(_Fragment, { children: [_jsxs("section", { "aria-label": "menu-categorias", style: { marginBottom: "1.5rem" }, children: [_jsx("h2", { style: { marginBottom: "0.75rem" }, children: "Categorias" }), categories.length === 0 ? (_jsx("p", { children: "Nenhuma categoria disponivel." })) : (_jsx("div", { style: { display: "flex", flexWrap: "wrap", gap: "0.5rem" }, children: categories.map((category) => (_jsx("span", { style: {
                                        border: "1px solid #d1d5db",
                                        borderRadius: "9999px",
                                        padding: "0.3rem 0.8rem",
                                        fontSize: "0.9rem"
                                    }, children: category.name }, category.id))) }))] }), _jsxs("section", { "aria-label": "destaques", style: { marginBottom: "1.5rem" }, children: [_jsx("h2", { style: { marginBottom: "0.75rem" }, children: "Destaques" }), highlights.length === 0 ? (_jsx("p", { children: "Nenhum destaque disponivel." })) : (_jsx("div", { style: { display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))", gap: "0.75rem" }, children: highlights.map((article) => (_jsxs("article", { style: { border: "1px solid #e5e7eb", borderRadius: "10px", padding: "0.9rem" }, children: [_jsx("h3", { style: { marginTop: 0 }, children: article.title }), _jsx("p", { style: { marginBottom: 0, color: "#374151" }, children: article.content.slice(0, 120) })] }, article.id))) }))] }), _jsxs("section", { "aria-label": "lista-materias", children: [_jsx("h2", { style: { marginBottom: "0.75rem" }, children: "Ultimas materias" }), articles.length === 0 ? (_jsx("p", { children: "Nenhuma materia publicada ate o momento." })) : (_jsx("div", { style: { display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(260px, 1fr))", gap: "0.75rem" }, children: articles.map((article) => (_jsxs("article", { style: { border: "1px solid #e5e7eb", borderRadius: "10px", padding: "0.9rem" }, children: [_jsx("div", { style: { fontSize: "0.85rem", color: "#6b7280" }, children: article.categoryName }), _jsx("h3", { style: { marginTop: "0.4rem", marginBottom: "0.4rem" }, children: article.title }), _jsx("p", { style: { marginBottom: 0, color: "#374151" }, children: article.content.slice(0, 180) })] }, article.id))) }))] })] })), activeTab === "auth" && (_jsxs("section", { "aria-label": "autenticacao", style: { display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(280px, 1fr))", gap: "1rem" }, children: [_jsxs("form", { onSubmit: handleLogin, style: { border: "1px solid #e5e7eb", borderRadius: "10px", padding: "1rem" }, children: [_jsx("h2", { style: { marginTop: 0 }, children: "Login" }), _jsx("label", { htmlFor: "login-email", children: "Email" }), _jsx("input", { id: "login-email", type: "email", value: loginEmail, onChange: (event) => setLoginEmail(event.target.value), required: true, style: { width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" } }), _jsx("button", { type: "submit", disabled: loginLoading, children: loginLoading ? "Entrando..." : "Entrar" }), loginResult && _jsx("p", { style: { marginTop: "0.75rem" }, children: loginResult })] }), _jsxs("form", { onSubmit: handleRegister, style: { border: "1px solid #e5e7eb", borderRadius: "10px", padding: "1rem" }, children: [_jsx("h2", { style: { marginTop: 0 }, children: "Cadastro" }), _jsx("label", { htmlFor: "register-name", children: "Nome" }), _jsx("input", { id: "register-name", type: "text", value: registerName, onChange: (event) => setRegisterName(event.target.value), required: true, style: { width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" } }), _jsx("label", { htmlFor: "register-email", children: "Email" }), _jsx("input", { id: "register-email", type: "email", value: registerEmail, onChange: (event) => setRegisterEmail(event.target.value), required: true, style: { width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" } }), _jsx("button", { type: "submit", disabled: registerLoading, children: registerLoading ? "Salvando..." : "Cadastrar" }), registerResult && _jsx("p", { style: { marginTop: "0.75rem" }, children: registerResult })] })] })), activeTab === "contact" && (_jsx("section", { "aria-label": "contato", style: { maxWidth: "680px" }, children: _jsxs("form", { onSubmit: handleContact, style: { border: "1px solid #e5e7eb", borderRadius: "10px", padding: "1rem" }, children: [_jsx("h2", { style: { marginTop: 0 }, children: "Fale Conosco" }), _jsx("label", { htmlFor: "department", children: "Departamento" }), _jsx("select", { id: "department", value: departmentId, onChange: (event) => setDepartmentId(event.target.value), required: true, style: { width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" }, children: departments.map((department) => (_jsx("option", { value: department.id, children: department.name }, department.id))) }), _jsx("label", { htmlFor: "contact-name", children: "Nome" }), _jsx("input", { id: "contact-name", type: "text", value: contactName, onChange: (event) => setContactName(event.target.value), required: true, style: { width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" } }), _jsx("label", { htmlFor: "contact-email", children: "Email" }), _jsx("input", { id: "contact-email", type: "email", value: contactEmail, onChange: (event) => setContactEmail(event.target.value), required: true, style: { width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" } }), _jsx("label", { htmlFor: "contact-message", children: "Mensagem" }), _jsx("textarea", { id: "contact-message", value: contactMessage, onChange: (event) => setContactMessage(event.target.value), required: true, rows: 5, style: { width: "100%", marginTop: "0.25rem", marginBottom: "0.75rem" } }), _jsx("button", { type: "submit", disabled: contactLoading || !departmentId, children: contactLoading ? "Enviando..." : "Enviar" }), contactResult && _jsx("p", { style: { marginTop: "0.75rem" }, children: contactResult })] }) }))] }));
}
