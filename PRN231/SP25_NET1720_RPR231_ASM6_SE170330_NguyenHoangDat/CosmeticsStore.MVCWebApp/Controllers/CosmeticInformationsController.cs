using CosmeticsStore.Repositories.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Newtonsoft.Json;

namespace CosmeticsStore.MVCWebApp.Controllers
{
    [Authorize]
    public class CosmeticInformationsController : Controller
    {
        private string APIEndPoint = "https://localhost:7200/api/";
        private string OdataEndPoint = "https://localhost:7200/odata/";
        // GET: CosmeticInformations
        public async Task<IActionResult> Index()
        {
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;

                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                using (var response = await httpClient.GetAsync(APIEndPoint + "CosmeticInformation"))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<List<CosmeticInformation>>(content);

                        if (result != null)
                        {
                            return View(result);
                        }
                    }
                }
            }
            return View(new List<CosmeticInformation>());
        }
        public async Task<IActionResult> Search(string? cosmeticName, string? cosmeticSize, string? skinType)
        {
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;

                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                var filter = $"$filter=contains(CosmeticName, '{cosmeticName}') and contains(CosmeticSize, '{cosmeticSize}') and contains(SkinType, '{skinType}')";
                var groupBy = "$apply=groupby((CosmeticName, CosmeticSize, SkinType))";
                var odataQuery = $"{OdataEndPoint}CosmeticInformation?{filter}&{groupBy}";

                using (var response = await httpClient.GetAsync(odataQuery))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();

                        var result = JsonConvert.DeserializeObject<ODataResponse<CosmeticInformation>>(content);

                        if (result != null)
                        {
                            return View("Index", result.Value);
                        }
                    }
                }
            }
            return View("Index", new List<CosmeticInformation>());
        }


        // GET: CosmeticInformations/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;

                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                using (var response = await httpClient.GetAsync(APIEndPoint + "CosmeticInformation/" + id))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<CosmeticInformation>(content);
                        if (result != null)
                        {
                            return View(result);
                        }
                    }
                }
            }
            return NotFound();
        }
        private async Task<List<CosmeticCategory>> GetCategorys()
        {
            var Categorys = new List<CosmeticCategory>();
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                using (var response = await httpClient.GetAsync(APIEndPoint + "CosmeticCategory"))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        Categorys = JsonConvert.DeserializeObject<List<CosmeticCategory>>(content);
                    }
                }
            }
            return Categorys;
        }

        // GET: CosmeticInformations/Create
        public async Task<IActionResult> Create()
        {
            ViewData["CategoryId"] = new SelectList(await GetCategorys(), "CategoryId", "CategoryName");
            return View();
        }

        // POST: CosmeticInformations/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("CosmeticId,CosmeticName,SkinType,ExpirationDate,CosmeticSize,DollarPrice,CategoryId")] CosmeticInformation cosmeticInformation)
        {
            if (ModelState.IsValid)
            {
                using (var httpClient = new HttpClient())
                {
                    #region Add Token to header of Request

                    var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                    httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                    #endregion

                    using (var response = await httpClient.PostAsJsonAsync(APIEndPoint + "CosmeticInformation", cosmeticInformation))
                    {
                        if (response.IsSuccessStatusCode)
                        {
                            var content = await response.Content.ReadAsStringAsync();
                            var result = JsonConvert.DeserializeObject<int>(content);
                            if (result > 0)
                            {
                                return RedirectToAction(nameof(Index));
                            }
                        }
                    }
                }

            }
            ViewData["CategoryId"] = new SelectList(await GetCategorys(), "CategoryId", "CategoryName", cosmeticInformation.CategoryId);
            return View(cosmeticInformation);
        }

        // GET: CosmeticInformations/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null)
            {
                return NotFound();
            }
            var Categorys = await GetCategorys();
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                using (var response = await httpClient.GetAsync(APIEndPoint + "CosmeticInformation/" + id))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<CosmeticInformation>(content);
                        if (result != null)
                        {
                            ViewData["CategoryId"] = new SelectList(Categorys, "CategoryId", "CategoryName", result.CategoryId);
                            return View(result);
                        }
                    }
                }
            }
            return NotFound();

        }

        // POST: CosmeticInformations/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string id, [Bind("CosmeticId,CosmeticName,SkinType,ExpirationDate,CosmeticSize,DollarPrice,CategoryId")] CosmeticInformation cosmeticInformation)
        {
            if (ModelState.IsValid)
            {
                using (var httpClient = new HttpClient())
                {
                    #region Add Token to header of Request

                    var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                    httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                    #endregion

                    using (var response = await httpClient.PutAsJsonAsync(APIEndPoint + "CosmeticInformation/" + id, cosmeticInformation))
                    {
                        if (response.IsSuccessStatusCode)
                        {
                            var content = await response.Content.ReadAsStringAsync();
                            var result = JsonConvert.DeserializeObject<int>(content);
                            if (result > 0)
                            {
                                return RedirectToAction(nameof(Index));
                            }
                        }
                    }
                }
            }
            ViewData["CategoryId"] = new SelectList(await GetCategorys(), "CategoryId", "CategoryName", cosmeticInformation.CategoryId);
            return View(cosmeticInformation);
        }

        // GET: CosmeticInformations/Delete/5
        public async Task<IActionResult> Delete(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;

                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                using (var response = await httpClient.GetAsync(APIEndPoint + "CosmeticInformation/" + id))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<CosmeticInformation>(content);
                        if (result != null)
                        {
                            return View(result);
                        }
                    }
                }
            }
            return NotFound();
        }

        // POST: CosmeticInformations/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                using (var response = await httpClient.DeleteAsync(APIEndPoint + "CosmeticInformation/" + id))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        return RedirectToAction(nameof(Index));
                    }
                }
            }

            return RedirectToAction(nameof(Index));
        }
    }
    public class ODataResponse<T>
    {
        [JsonProperty("value")]
        public List<T> Value { get; set; }
    }
}
