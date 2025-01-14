- Tạo solution rỗng, đặt tên đứng format

- Tạo Class Library Project, đặt tên "Viết tắt project".Repositories, xóa Class1.cs

dotnet add package Microsoft.EntityFrameworkCore --version 8.0.5
dotnet add package Microsoft.EntityFrameworkCore.SqlServer --version 8.0.5
dotnet add package Microsoft.EntityFrameworkCore.Tools --version 8.0.5
dotnet add package Microsoft.Extensions.Configuration --version 8.0.0
dotnet add package Microsoft.Extensions.Configuration.Json --version 8.0.0
- Copy đoạn trên, Open in Terminal Repositories, bỏ vào và Enter

- Mở reverse engineer của ef core
- add database connection
- Nhập thông tin và chọn database
- Ok, đợi, chọn table rồi Ok
- Click Include connection string

- Tạo appsettings.json
[
    "ConnectionStrings"
    {
        "DefaultConnection": "connectionString"
    }
]
- Thay "connectionString" bằng connection string trong context

- Thay OnCofiguring() bằng câu lệnh dưới:
public static string GetConnectionString(string connectionStringName)
{
    var config = new ConfigurationBuilder()
        .SetBasePath(AppDomain.CurrentDomain.BaseDirectory)
        .AddJsonFile("appsettings.json")
        .Build();

    string connectionString = config.GetConnectionString(connectionStringName);
    return connectionString;
}

protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    => optionsBuilder.UseSqlServer(GetConnectionString("DefaultConnection")).UseQueryTrackingBehavior(QueryTrackingBehavior.NoTracking);

- Tạo folder DBContext
- Bỏ class context vào folder đó
- Đổi namespace, using các class

- Tạo folder Base
- Tạo class GenericRepository, xóa đi để lại namespace
- Copy bỏ vô đoạn dưới
public class GenericRepository<T> where T : class
{
    protected zPaymentContext _context;

    public GenericRepository()
    {
        _context ??= new zPaymentContext();
    }

    public GenericRepository(zPaymentContext context)
    {
        _context = context;
    }

    public List<T> GetAll()
    {
        return _context.Set<T>().ToList();
    }
    public async Task<List<T>> GetAllAsync()
    {
        return await _context.Set<T>().ToListAsync();
    }
    public void Create(T entity)
    {
        _context.Add(entity);
        _context.SaveChanges();
    }

    public async Task<int> CreateAsync(T entity)
    {
        _context.Add(entity);
        return await _context.SaveChangesAsync();
    }

    public void Update(T entity)
    {
        var tracker = _context.Attach(entity);
        tracker.State = EntityState.Modified;
        _context.SaveChanges();

        //if (_context.Entry(entity).State == EntityState.Detached)
        //{
        //    var tracker = _context.Attach(entity);
        //    tracker.State = EntityState.Modified;
        //}
        //_context.SaveChanges();
    }

    public async Task<int> UpdateAsync(T entity)
    {
        //var trackerEntity = _context.Set<T>().Local.FirstOrDefault(e => e == entity);
        //if (trackerEntity != null)
        //{
        //    _context.Entry(trackerEntity).State = EntityState.Detached;
        //}
        //var tracker = _context.Attach(entity);
        //tracker.State = EntityState.Modified;
        //return await _context.SaveChangesAsync();

        var tracker = _context.Attach(entity);
        tracker.State = EntityState.Modified;
        return await _context.SaveChangesAsync();

        //if (_context.Entry(entity).State == EntityState.Detached)
        //{
        //    var tracker = _context.Attach(entity);
        //    tracker.State = EntityState.Modified;
        //}

        //return await _context.SaveChangesAsync();
    }

    public bool Remove(T entity)
    {
        _context.Remove(entity);
        _context.SaveChanges();
        return true;
    }

    public async Task<bool> RemoveAsync(T entity)
    {
        _context.Remove(entity);
        await _context.SaveChangesAsync();
        return true;
    }

    public T GetById(int id)
    {
        var entity = _context.Set<T>().Find(id);
        if (entity != null)
        {
            _context.Entry(entity).State = EntityState.Detached;
        }

        return entity;

        //return _context.Set<T>().Find(id);
    }

    public async Task<T> GetByIdAsync(int id)
    {
        var entity = await _context.Set<T>().FindAsync(id);
        if (entity != null)
        {
            _context.Entry(entity).State = EntityState.Detached;
        }

        return entity;

        //return await _context.Set<T>().FindAsync(id);
    }

    public T GetById(string code)
    {
        var entity = _context.Set<T>().Find(code);
        if (entity != null)
        {
_context.Entry(entity).State = EntityState.Detached;
        }

        return entity;

        //return _context.Set<T>().Find(code);
    }

    public async Task<T> GetByIdAsync(string code)
    {
        var entity = await _context.Set<T>().FindAsync(code);
        if (entity != null)
        {
            _context.Entry(entity).State = EntityState.Detached;
        }

        return entity;

        //return await _context.Set<T>().FindAsync(code);
    }

    public T GetById(Guid code)
    {
        var entity = _context.Set<T>().Find(code);
        if (entity != null)
        {
            _context.Entry(entity).State = EntityState.Detached;
        }

        return entity;

        //return _context.Set<T>().Find(code);
    }

    public async Task<T> GetByIdAsync(Guid code)
    {
        var entity = await _context.Set<T>().FindAsync(code);
        if (entity != null)
        {
            _context.Entry(entity).State = EntityState.Detached;
        }

        return entity;

        //return await _context.Set<T>().FindAsync(code);
    }

    #region Separating asigned entity and save operators        

    public void PrepareCreate(T entity)
    {
        _context.Add(entity);
    }

    public void PrepareUpdate(T entity)
    {
        var tracker = _context.Attach(entity);
        tracker.State = EntityState.Modified;
    }

    public void PrepareRemove(T entity)
    {
        _context.Remove(entity);
    }

    public int Save()
    {
        return _context.SaveChanges();
    }

    public async Task<int> SaveAsync()
    {
        return await _context.SaveChangesAsync();
    }

    #endregion Separating asign entity and save operators
}
- Đổi lấy context
- Xóa comment và seperating cuối cùng

- Tạo Repo riêng ModelRepository, chuyển public class
- extend GenericRepository<Model>
- Tạo hàm riêng nếu cần thiết như dưới:
public new async Task<List<Schedule>> GetAll()
{
    var schedules = await _context.Schedules.Include(s => s.User).ToListAsync();
    return schedules;
}

public async Task<List<Schedule>> Search(Guid id, Guid userId, DateTime workDate, string status)
{
    var schedules = await _context.Schedules
        .Where(s => (id == Guid.Empty || s.Id == id)
                    && (userId == Guid.Empty || s.UserId == userId)
                    && (workDate == DateTime.MinValue || s.WorkDate == workDate)
                    && (string.IsNullOrEmpty(status) || s.Status == status))
        .Include(s => s.User)
        .ToListAsync();
    return schedules;
}

- GetUserAccount trong UserAccountRepository
        public async Task<UserAccount?> GetUserAccount(string userName, string password) => await _context.UserAccounts.FirstOrDefaultAsync(u => u.UserName == userName && u.Password == password && u.IsActive);
* Check IsActive là gì? Đề có yêu cầu không? Query db để chắc ăn trả về gì
* Login bằng cái gì? UserName Email Phone EmployeeCode...

- Tạo lớp Services Prj.Services, xóa Class1.cs
- References Repo
- Tạo đủ các Service với xxxService	