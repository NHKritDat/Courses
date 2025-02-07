using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;
using SCBS.Repositories.DBContext;
using SCBS.Repositories.Models;

namespace SCBS.MVCWebApp.Controllers
{
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

        //// GET: Schedules/Create
        //public IActionResult Create()
        //{
        //    ViewData["UserId"] = new SelectList(_context.Users, "Id", "Email");
        //    return View();
        //}

        //// POST: Schedules/Create
        //// To protect from overposting attacks, enable the specific properties you want to bind to.
        //// For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        //[HttpPost]
        //[ValidateAntiForgeryToken]
        //public async Task<IActionResult> Create([Bind("Id,UserId,WorkDate,Status,CreatedAt,UpdatedAt")] Schedule schedule)
        //{
        //    if (ModelState.IsValid)
        //    {
        //        schedule.Id = Guid.NewGuid();
        //        _context.Add(schedule);
        //        await _context.SaveChangesAsync();
        //        return RedirectToAction(nameof(Index));
        //    }
        //    ViewData["UserId"] = new SelectList(_context.Users, "Id", "Email", schedule.UserId);
        //    return View(schedule);
        //}

        //// GET: Schedules/Edit/5
        //public async Task<IActionResult> Edit(Guid? id)
        //{
        //    if (id == null)
        //    {
        //        return NotFound();
        //    }

        //    var schedule = await _context.Schedules.FindAsync(id);
        //    if (schedule == null)
        //    {
        //        return NotFound();
        //    }
        //    ViewData["UserId"] = new SelectList(_context.Users, "Id", "Email", schedule.UserId);
        //    return View(schedule);
        //}

        //// POST: Schedules/Edit/5
        //// To protect from overposting attacks, enable the specific properties you want to bind to.
        //// For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        //[HttpPost]
        //[ValidateAntiForgeryToken]
        //public async Task<IActionResult> Edit(Guid id, [Bind("Id,UserId,WorkDate,Status,CreatedAt,UpdatedAt")] Schedule schedule)
        //{
        //    if (id != schedule.Id)
        //    {
        //        return NotFound();
        //    }

        //    if (ModelState.IsValid)
        //    {
        //        try
        //        {
        //            _context.Update(schedule);
        //            await _context.SaveChangesAsync();
        //        }
        //        catch (DbUpdateConcurrencyException)
        //        {
        //            if (!ScheduleExists(schedule.Id))
        //            {
        //                return NotFound();
        //            }
        //            else
        //            {
        //                throw;
        //            }
        //        }
        //        return RedirectToAction(nameof(Index));
        //    }
        //    ViewData["UserId"] = new SelectList(_context.Users, "Id", "Email", schedule.UserId);
        //    return View(schedule);
        //}

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

        //// POST: Schedules/Delete/5
        //[HttpPost, ActionName("Delete")]
        //[ValidateAntiForgeryToken]
        //public async Task<IActionResult> DeleteConfirmed(Guid id)
        //{
        //    var schedule = await _context.Schedules.FindAsync(id);
        //    if (schedule != null)
        //    {
        //        _context.Schedules.Remove(schedule);
        //    }

        //    await _context.SaveChangesAsync();
        //    return RedirectToAction(nameof(Index));
        //}

        //private bool ScheduleExists(Guid id)
        //{
        //    return _context.Schedules.Any(e => e.Id == id);
        //}
    }
}
