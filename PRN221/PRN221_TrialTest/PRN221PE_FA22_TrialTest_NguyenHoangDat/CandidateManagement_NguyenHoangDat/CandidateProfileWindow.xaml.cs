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
    /// Interaction logic for CandidateProfileWindow.xaml
    /// </summary>
    public partial class CandidateProfileWindow : Window
    {
        private readonly ICandidateProfileService _candidateProfileService;
        private readonly IJobPostingService _jobPostingService;
        public CandidateProfileWindow()
        {
            InitializeComponent();
            _candidateProfileService = new CandidateProfileService();
            _jobPostingService = new JobPostingService();
        }

        private void btnClose_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void btnAdd_Click(object sender, RoutedEventArgs e)
        {

        }

        private void btnDelete_Click(object sender, RoutedEventArgs e)
        {

        }

        private void btnUpdate_Click(object sender, RoutedEventArgs e)
        {

        }

        private void Loaded_JobPosting(object sender, RoutedEventArgs e)
        {
            try
            {
                var job = _jobPostingService.GetJobPosting();
                cboJobPosting.ItemsSource = job;
                cboJobPosting.DisplayMemberPath = "JobPostingTitle";
                cboJobPosting.SelectedValue = "PostingId";
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Load posting list failed!");
            }
        }

        private void Loaded_CandidateProfile(object sender, RoutedEventArgs e)
        {
            try
            {
                var candidateProfile = _candidateProfileService.GetCandidates();
                candidateProfile.ForEach(c=> c.Posting=_jobPostingService.GetJobPosting(c.PostingId));
                datCandidateProfile.ItemsSource = candidateProfile;
                datCandidateProfile.Columns[6].Visibility = Visibility.Hidden;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Load candidate profile list failed!");
            }
        }
    }
}
