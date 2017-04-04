package com.besafx.app;

import com.besafx.app.component.ScheduledTasks;
import com.besafx.app.config.EmailSender;
import com.besafx.app.rest.TaskOperationRest;
import com.besafx.app.search.TaskSearch;
import com.besafx.app.service.*;
import com.besafx.app.util.IOCopier;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import com.dropbox.core.v2.users.FullAccount;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTests {

    private static final String ACCESS_TOKEN = "_kj_6GrBNbYAAAAAAACR03IkJ8JnT5i1AntPiHU9GiLCsT7zO_LxIJMUEMk93T9O";

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private PersonService personService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TaskService taskService;

    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACefec35545cc868089dadc7fade4eafb6";
    public static final String AUTH_TOKEN = "df45ca6b7e27f746d764f8fb79fb2131";

    private LocalDate today = new DateTime().withTimeAtStartOfDay().toLocalDate();

    private LocalDate tomorrow = new DateTime().plusDays(1).withTimeAtStartOfDay().toLocalDate();

    private String message = "";

    @Autowired
    private TaskSearch taskSearch;

    @Autowired
    private TaskOperationService taskOperationService;

    @Autowired
    private TaskOperationRest taskOperationRest;

    @Autowired
    private ScheduledTasks scheduledTasks;

    @Autowired
    private TaskToService taskToService;

    @Test
    public void contextLoads() throws Exception {

        // minfyJS();

        //concatFiles();

        //sendAdsEmail();

        //sendUpdate();

//        LocalDate today = new DateTime().withTimeAtStartOfDay().toLocalDate();
//        LocalDate weekStart = today.withDayOfWeek(DateTimeConstants.SATURDAY);
//        LocalDate weekEnd = today.withDayOfWeek(DateTimeConstants.SATURDAY).plusDays(6);
//        System.out.println(weekStart);
//        System.out.println(weekEnd);

//        scheduledTasks.warnAllAboutUnCommentedTasksAtMidNight();

//        LocalDate yesterday = new DateTime().minusDays(1).withTimeAtStartOfDay().toLocalDate();
//        LocalDate today = new DateTime().withTimeAtStartOfDay().toLocalDate();
//        LocalDate tomorrow = new DateTime().plusDays(1).withTimeAtStartOfDay().toLocalDate();

/**
 //Run evening task (Time of execution = 14)
 //Round(Morning)
 DateTime startLast24Hour = new DateTime().minusDays(1).withTime(14, 0, 0, 0);
 DateTime startLast12Hour = new DateTime().withTime(2, 0, 0, 0);
 DateTime endLast12Hour = new DateTime().withTime(14, 0, 0, 0);

 //Get all opened tasks
 List<Task> tasks = taskSearch.search(null, null, null, null, null, null, null, true, true, "All", null);
 tasks.stream().forEach(task -> {
 task.getTaskTos().stream().map(TaskTo::getPerson).forEach(person -> {
 //Count operations for this person on this task
 long numberOfOperations = taskOperationService.countByTaskAndSenderAndTypeAndDateAfterAndDateBefore(task, person, 1, startLast12Hour.toDate(), endLast12Hour.toDate());
 if(numberOfOperations == 0){
 long numberOfWarns = taskOperationService.countByTaskAndSenderAndTypeAndDateAfterAndDateBefore(task, person, 2, startLast24Hour.toDate(), startLast12Hour.toDate());
 if(numberOfWarns == 0){
 //Send Warn
 try {
 TaskOperation taskOperation = new TaskOperation();
 StringBuilder builder = new StringBuilder();
 builder.append("تحذير بالخصم بشأن عدم التعامل مع المهمة رقم / " + task.getCode());
 builder.append(" ");
 builder.append("للموظف / " + person.getName());
 builder.append(" ");
 builder.append("من الفترة / " + new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(startLast12Hour.toDate()));
 builder.append(" ");
 builder.append("إلى الفترة / " + new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(endLast12Hour.toDate()));
 builder.append(" ");
 builder.append("نأمل الإلتزام بالتعليق فى خلال مدة لا تزيد عن 12 ساعة.");
 taskOperation.setContent(builder.toString());
 taskOperation.setType(2);
 taskOperation.setTask(task);
 taskOperationRest.create(taskOperation, task.getPerson());
 } catch (IOException e) {
 e.printStackTrace();
 }
 }else{
 //Send Discount
 try {
 TaskOperation taskOperation = new TaskOperation();
 StringBuilder builder = new StringBuilder();
 builder.append("خصم بمقدار 50 ريال سعودي بشأن عدم التعامل مع المهمة رقم / " + task.getCode());
 builder.append(" ");
 builder.append("للموظف / " + person.getName());
 builder.append(" ");
 builder.append("نظراً لتحذيره سابقاً");
 builder.append(" ");
 builder.append("من الفترة / " + new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(startLast24Hour.toDate()));
 builder.append(" ");
 builder.append("إلى الفترة / " + new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(startLast12Hour.toDate()));
 builder.append(" ");
 builder.append("نأمل منه مراجعة جهة التكليف.");
 taskOperation.setContent(builder.toString());
 taskOperation.setType(3);
 taskOperation.setTask(task);
 taskOperationRest.create(taskOperation, task.getPerson());
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 }
 });
 });
 */

//Lists.newArrayList(taskOperationService.findAll()).stream().forEach(taskOperation -> {
//    taskOperation.setType(1);
//    taskOperationService.save(taskOperation);
//});

//        //Run morning task(Time of execution = 2)
//        //Round(Evening)
//        DateTime startEvening = new DateTime().minusDays(1).withTime(14, 0, 0, 0);
//        DateTime endEvening = new DateTime().withTime(2, 0, 0, 0);


//        scheduledTasks.warnAllAboutUnCommentedTasksAtAfternoon();

//        taskSearch.getIncomingTasks("All", personService.findByEmail("ararnni@gmail.com").getId()).stream().forEach(task -> System.out.println(task.getTaskOperations().size()));

//        System.out.println(taskToService.findByTaskIdAndPersonId(Long.valueOf(180), Long.valueOf(1221)).getId());

//        taskService.findAll().forEach(task -> {
//            task.setWarn(2);
//            task.setDeduction(Double.valueOf(50));
//            taskService.save(task);
//        });

//        for (TaskTo taskTo : taskToService.findAll()) {
//            taskTo.setProgress(0);
//            taskTo.setClosed(false);
//            taskToService.save(taskTo);
//        }


    }

    public void sendUpdate() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/mailTemplate/Update.html");
        String message = IOUtils.toString(classPathResource.getInputStream(), Charset.defaultCharset());
        Lists.newArrayList(personService.findAll()).stream().forEach(person -> emailSender.send("آخر التطورات والتحديثات فى موقع المعهد", message, person.getEmail()));
    }

    public void sendAdsEmail() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/mailTemplate/Ads.html");
        String message = IOUtils.toString(classPathResource.getInputStream(), Charset.defaultCharset());
        emailSender.send("نظام ادارة المعاهد الإلكترونية المتكاملة", message,
//                "islamhaker@gmail.com",
                "amz7680@gmail.com"
//                "info@ipa.edu.sa",
//                "Dammam_info@ipa.edu.sa",
//                "ASIRIPA@IPA.EDU.SA",
//                "wipadammam_info@IPA.EDU.SA",
//                "info@araburban.org",
//                "hajj@uqu.edu.sa",
//                "research@kfsc.edu.sa",
//                "info@rpi.edu.sa",
//                "info@tvtc.gov.sa",
//                "info@berlitz.sa",
//                "mohamed.ezz@berlitz.sa",
//                "lutfa@berlitz.sa",
//                "wed@berlitz.sa",
//                "wessam@berlitz.sa",
//                "zohoor.alawi@berlitz.sa",
//                "sharaf@berlitz.sa",
//                "yasmin@berlitz.sa",
//                "shady@berlitz.sa",
//                "Mashael.alshareef@berlitz.sa",
//                "info@inlingua-riyadh.com",
//                "DPI@aaa.edu.sa"
        );
    }

    public void concatFiles() throws IOException {

        List<ClassPathResource> classPathResourceList = new ArrayList<>();

        try {
            File file = new ClassPathResource("util/scripts.txt").getFile();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                classPathResourceList.add(new ClassPathResource(line));
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File("src/main/resources/static/ui/app.js");
        file.delete();

        IOCopier.joinFiles(file, classPathResourceList.stream().map(classPathResource -> {
            try {
                return classPathResource.getFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList()));
    }

    private void minfyJS() throws IOException {
        final URL url = new URL("https://javascript-minifier.com/raw");

        // JS File you want to compress
        byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/static/ui/app.js"));

        final StringBuilder data = new StringBuilder();
        data.append(URLEncoder.encode("input", "UTF-8"));
        data.append('=');
        data.append(URLEncoder.encode(new String(bytes), "UTF-8"));

        bytes = data.toString().getBytes("UTF-8");

        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(bytes.length));

        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(bytes);
        }

        final int code = conn.getResponseCode();

        System.out.println("Status: " + code);

        if (code == 200) {
            System.out.println("----");
            final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.print(inputLine);
            }
            in.close();

            System.out.println("\n----");
        } else {
            System.out.println("Oops");
        }
    }

    private void initDropBox() throws Exception {
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").withUserLocale("en_US").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        // Get current account info
        FullAccount account;
        account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());

        // Get files and folder metadata from Dropbox root directory
        ListFolderResult result = client.files().listFolder("");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }

        // Upload "test.txt" to Dropbox
        InputStream in = new FileInputStream("C://diagram.png");
        FileMetadata metadata = client.files().uploadBuilder("/bassam/file.png").uploadAndFinish(in);

        //get link to download
        //System.out.println(client.files().getTemporaryLink("/file.png").getLink());

        //Create Share
        //client.sharing().unshareFile("/file.png");
        client.sharing().createSharedLinkWithSettings("/file.png");

        ListSharedLinksResult listSharedLinksResult = client.sharing().listSharedLinksBuilder()
                .withPath("/file.png").withDirectOnly(true).start();

//        System.out.println(listSharedLinksResult.getLinks().get(0).getPathLower());
//        System.out.println(listSharedLinksResult.getLinks().get(0).getUrl().replaceAll("dl=0", "raw=1"));


        //System.out.println(client.sharing().getFileMetadata("/file.png"));

    }
}
