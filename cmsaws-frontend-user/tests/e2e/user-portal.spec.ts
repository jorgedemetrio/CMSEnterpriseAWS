import { expect, test } from "@playwright/test";

test.describe("Camada de Usuarios - Portal", () => {
  test("deve carregar home com titulo e cabecalho", async ({ page }) => {
    await page.goto("/");

    await expect(page).toHaveTitle(/NewsFlow - User Portal/);
    await expect(page.getByRole("heading", { name: "NewsFlow User Portal" })).toBeVisible();
  });

  test("deve exibir secoes principais da home", async ({ page }) => {
    await page.goto("/");

    await expect(page.getByRole("heading", { name: "Categorias" })).toBeVisible();
    await expect(page.getByRole("heading", { name: "Destaques" })).toBeVisible();
    await expect(page.getByRole("heading", { name: "Ultimas materias" })).toBeVisible();
  });

  test("deve responder na rota raiz com conteudo renderizado", async ({ page }) => {
    const response = await page.goto("/");

    expect(response).not.toBeNull();
    expect(response?.ok()).toBeTruthy();
    await expect(page.locator("main")).toBeVisible();
  });
});
