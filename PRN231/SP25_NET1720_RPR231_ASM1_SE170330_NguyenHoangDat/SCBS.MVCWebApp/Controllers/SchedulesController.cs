using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;
using SCBS.Repositories.Models;

namespace SCBS.MVCWebApp.Controllers
{
    [Authorize]
    public class SchedulesController : Controller
    {
        private string APIEndPoint = "https://localhost:7023/api/";
        public SchedulesController()
        {
        }
        public async Task<IActionResult> Index()
        {
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;

                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion


                using (var response = await httpClient.GetAsync(APIEndPoint + "Schedule"))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<List<Schedule>>(content);

                        if (result != null)
                        {
                            return View(result);
                        }
                    }
                }
            }
            return View(new List<Schedule>());
        }

        public async Task<IActionResult> Details(string id)
        {
            using (var httpClient = new HttpClient())
            {
                #region Add Token to header of Request

                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;

                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                #endregion

                using (var response = await httpClient.GetAsync(APIEndPoint + "Schedule/" + id))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<Schedule>(content);
                        if (result != null)
                        {
                            return View(result);
                        }
                    }
                }
            }
            return View(new Schedule());
        }
        //private readonly NET1720_PRN231_PRJ_G2_SkinCareBookingSystemContext _context;

        //public SchedulesController(NET1720_PRN231_PRJ_G2_SkinCareBookingSystemContext context)
        //{
        //    _context = context;
        //}

        //// GET: Schedules
        //public async Task<IActionResult> Index()
        //{
        //    var nET1720_PRN231_PRJ_G2_SkinCareBookingSystemContext = _context.Schedules.Include(s => s.User);
        //    return View(await nET1720_PRN231_PRJ_G2_SkinCareBookingSystemContext.ToListAsync());
        //}

        //// GET: Schedules/Details/5
        //public async Task<IActionResult> Details(Guid? id)
        //{
        //    if (id == null)
        //    {
        //        return NotFound();
        //    }

        //    var schedule = await _context.Schedules
        //        .Include(s => s.User)
        //        .FirstOrDefaultAsync(m => m.Id == id);
        //    if (schedule == null)
        //    {
        //        return NotFound();
        //    }

        //    return View(schedule);
        //}

        // GET: Schedules/Create
        public async Task<IActionResult> Create()
        {
            ViewData["UserId"] = new SelectList(await GetUsers(), "Id", "Email");
            return View();
        }

        // POST: Schedules/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,UserId,WorkDate,Status,CreatedAt,UpdatedAt")] Schedule schedule)
        {
            if (ModelState.IsValid)
            {
                using (var httpClient = new HttpClient())
                {
                    var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                    httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);
                    using (var response = await httpClient.PostAsJsonAsync(APIEndPoint + "Schedule", schedule))
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
            ViewData["UserId"] = new SelectList(await GetUsers(), "Id", "Email", schedule.UserId);
            return View(schedule);
        }

        // GET: Schedules/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            var users = await GetUsers();

            using (var httpClient = new HttpClient())
            {
                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);
                using (var response = await httpClient.GetAsync(APIEndPoint + "Schedule/" + id))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<Schedule>(content);
                        if (result != null)
                        {
                            ViewData["UserId"] = new SelectList(users, "Id", "Email", result.UserId);
                            return View(result);
                        }
                    }
                }
            }

            ViewData["UserId"] = new SelectList(users, "Id", "Email");
            return View(new Schedule());
        }

        // POST: Schedules/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Schedule schedule)
        {
            var saveStatus = false;
            if (ModelState.IsValid)
            {
                try
                {

                    using (var httpClient = new HttpClient())
                    {
                        var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                        httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);
                        using (var response = await httpClient.PutAsJsonAsync(APIEndPoint + "Schedule/" + schedule.Id, schedule))
                        {
                            if (response.IsSuccessStatusCode)
                            {
                                var content = await response.Content.ReadAsStringAsync();
                                var result = JsonConvert.DeserializeObject<int>(content);
                                if (result > 0)
                                {
                                    saveStatus = true;
                                }
                            }
                        }
                    }
                }
                catch (DbUpdateConcurrencyException)
                {
                    throw;
                }
            }
            if (saveStatus)
            {
                return RedirectToAction(nameof(Index));
            }
            else
            {
                ViewData["UserId"] = new SelectList(await GetUsers(), "Id", "Email", schedule.UserId);
                return View(schedule);
            }
        }

        private async Task<List<User>> GetUsers()
        {
            var users = new List<User>();
            using (var httpClient = new HttpClient())
            {
                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);
                using (var response = await httpClient.GetAsync(APIEndPoint + "User"))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        var content = await response.Content.ReadAsStringAsync();
                        users = JsonConvert.DeserializeObject<List<User>>(content);
                    }
                }
            }
            return users;
        }

        //// GET: Schedules/Delete/5
        //public async Task<IActionResult> Delete(Guid? id)
        //{
        //    if (id == null)
        //    {
        //        return NotFound();
        //    }

        //    var schedule = await _context.Schedules
        //        .Include(s => s.User)
        //        .FirstOrDefaultAsync(m => m.Id == id);
        //    if (schedule == null)
        //    {
        //        return NotFound();
        //    }

        //    return View(schedule);
        //}

        // POST: Schedules/Delete/5
        public async Task<IActionResult> DeleteConfirmed(Guid id)
        {
            bool deleteStatus = false;
            using (var httpClient = new HttpClient())
            {
                var tokenString = HttpContext.Request.Cookies.FirstOrDefault(c => c.Key == "TokenString").Value;
                httpClient.DefaultRequestHeaders.Add("Authorization", "Bearer " + tokenString);

                using (var response = await httpClient.DeleteAsync(APIEndPoint + "Schedule/" + id))
                {
                    if (response.IsSuccessStatusCode)
                    {
                        deleteStatus = true;
                    }
                }
            }
            return RedirectToAction(nameof(Index));
        }

        //private bool ScheduleExists(Guid id)
        //{
        //    return _context.Schedules.Any(e => e.Id == id);
        //}
    }
}
