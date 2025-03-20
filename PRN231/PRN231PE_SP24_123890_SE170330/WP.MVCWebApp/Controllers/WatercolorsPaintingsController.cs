using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Newtonsoft.Json;
using WP.Repositories.Models;

namespace WP.MVCWebApp.Controllers
{
    [Authorize]
    public class WatercolorsPaintingsController : Controller
    {
        private string APIEndPoint = "https://localhost:7015/api/";
        private string OdataEndPoint = "https://localhost:7015/odata/";
        // GET: WatercolorsPaintings
        [Authorize(Roles = "3,2")]
        public async Task<IActionResult> Index()
        {
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;

                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                using (var response = await httpClient.GetAsync(APIEndPoint + "WatercolorsPainting"))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<List<WatercolorsPainting>>(content);

                        if (result != null)
                        {
                            return View(result);
                        }
                    }
                }
            }
            return View(new List<WatercolorsPainting>());
        }
        [Authorize(Roles = "3,2")]
        public async Task<IActionResult> Search(string? paintingAuthor, int? publishYear)
        {
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;

                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                var filter = string.Empty;
                if (publishYear == null || publishYear == 0)
                {
                    filter = $"$filter=contains(PaintingAuthor, '{paintingAuthor}')";
                }
                else
                {
                    filter = $"$filter=contains(PaintingAuthor, '{paintingAuthor}') and PublishYear eq {publishYear}";
                }
                var odataQuery = $"{OdataEndPoint}WatercolorsPainting?{filter}";

                using (var response = await httpClient.GetAsync(odataQuery))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();

                        var result = JsonConvert.DeserializeObject<ODataResponse<WatercolorsPainting>>(content);

                        if (result != null)
                        {
                            return View("Index", result.Value);
                        }
                    }
                }
            }
            return View("Index", new List<WatercolorsPainting>());
        }
        [Authorize(Roles = "3")]
        // GET: WatercolorsPaintings/Details/5
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

                using (var response = await httpClient.GetAsync(APIEndPoint + "WatercolorsPainting/" + id))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<WatercolorsPainting>(content);
                        if (result != null)
                        {
                            return View(result);
                        }
                    }
                }
            }
            return NotFound();
        }
        private async Task<List<Style>> GetStyles()
        {
            var styles = new List<Style>();
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                using (var response = await httpClient.GetAsync(APIEndPoint + "Style"))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        styles = JsonConvert.DeserializeObject<List<Style>>(content);
                    }
                }
            }
            return styles;
        }

        [Authorize(Roles = "3")]
        // GET: WatercolorsPaintings/Create
        public async Task<IActionResult> Create()
        {
            ViewData["StyleId"] = new SelectList(await GetStyles(), "StyleId", "StyleName");
            return View();
        }

        // POST: WatercolorsPaintings/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "3")]
        public async Task<IActionResult> Create([Bind("PaintingId,PaintingName,PaintingDescription,PaintingAuthor,Price,PublishYear,CreatedDate,StyleId")] WatercolorsPainting watercolorsPainting)
        {
            if (ModelState.IsValid)
            {
                using (var httpClient = new HttpClient())
                {
                    #region Add Token to header of Request

                    var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                    httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                    #endregion

                    using (var response = await httpClient.PostAsJsonAsync(APIEndPoint + "WatercolorsPainting", watercolorsPainting))
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
            ViewData["StyleId"] = new SelectList(await GetStyles(), "StyleId", "StyleName", watercolorsPainting.StyleId);
            return View(watercolorsPainting);
        }

        // GET: WatercolorsPaintings/Edit/5
        [Authorize(Roles = "3")]
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null)
            {
                return NotFound();
            }
            var styles = await GetStyles();
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                using (var response = await httpClient.GetAsync(APIEndPoint + "WatercolorsPainting/" + id))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<WatercolorsPainting>(content);
                        if (result != null)
                        {
                            ViewData["StyleId"] = new SelectList(styles, "StyleId", "StyleName", result.StyleId);
                            return View(result);
                        }
                    }
                }
            }
            return NotFound();
        }

        // POST: WatercolorsPaintings/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "3")]
        public async Task<IActionResult> Edit(string id, [Bind("PaintingId,PaintingName,PaintingDescription,PaintingAuthor,Price,PublishYear,CreatedDate,StyleId")] WatercolorsPainting watercolorsPainting)
        {
            if (id != watercolorsPainting.PaintingId)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                using (var httpClient = new HttpClient())
                {
                    #region Add Token to header of Request

                    var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                    httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                    #endregion

                    using (var response = await httpClient.PutAsJsonAsync(APIEndPoint + "WatercolorsPainting/" + id, watercolorsPainting))
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
            ViewData["StyleId"] = new SelectList(await GetStyles(), "StyleId", "StyleName", watercolorsPainting.StyleId);
            return View(watercolorsPainting);
        }

        // GET: WatercolorsPaintings/Delete/5
        [Authorize(Roles = "3")]
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

                using (var response = await httpClient.GetAsync(APIEndPoint + "WatercolorsPainting/" + id))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<WatercolorsPainting>(content);
                        if (result != null)
                        {
                            return View(result);
                        }
                    }
                }
            }
            return NotFound();
        }

        // POST: WatercolorsPaintings/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "3")]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                using (var response = await httpClient.DeleteAsync(APIEndPoint + "WatercolorsPainting/" + id))
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
