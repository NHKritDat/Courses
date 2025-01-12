using Candidate_BusinessObjects;
using Candidate_Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace CandidateManagement_NguyenHoangDat
{
    /// <summary>
    /// Interaction logic for JobPosting.xaml
    /// </summary>
    public partial class JobPostingWindown : Window
    {
        private IJobPostingService jobPostingService = null;
        public JobPostingWindown()
        {
            InitializeComponent();
            if (jobPostingService == null)
                jobPostingService = new JobPostingService();
        }

        private void btnClose_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void btnCreate_Click(object sender, RoutedEventArgs e)
        {
            JobPosting job = new JobPosting();
            job.PostingId = txtPostingID.Text;
            job.Description = txtDescription.Text;
            job.JobPostingTitle = txtTitle.Text;
            job.PostedDate = DateTime.Parse(datPostDate.Text);
            if (jobPostingService.WriteJson(job))
            {
                LoadInitData();
                MessageBox.Show("Add Successfully!");
            }
            else
            {
                MessageBox.Show("Please contact with 113!");
            }
        }

        private void LoadInitData()
        {
            this.dtgJobPosting.ItemsSource = jobPostingService.GetJobPosting();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            LoadInitData();
        }

        private void btnDelete_Click(object sender, RoutedEventArgs e)
        {
            if (jobPostingService.RemoveJobPosting(txtPostingID.Text))
            {
                LoadInitData();
                MessageBox.Show("Remove Successfully!");
            }
            else
            {
                MessageBox.Show("Please contact with 113!");
            }
        }

        private void dtgJobPosting_SelectedCellsChanged(object sender, SelectedCellsChangedEventArgs e)
        {
            DataGrid dg = sender as DataGrid;
            DataGridRow row = (DataGridRow) dg.ItemContainerGenerator.ContainerFromIndex(dg.SelectedIndex);
            DataGridCell cell = (DataGridCell) dg.Columns[0].GetCellContent(row).Parent;
            string id = ((TextBlock)cell.Content).Text;
            JobPosting job = jobPostingService.GetJobPosting(id);
            txtDescription.Text = job.Description;
            txtPostingID.Text = job.PostingId;
            txtTitle.Text = job.JobPostingTitle;
            datPostDate.Text = job.PostedDate.ToString();
        }
    }
}
