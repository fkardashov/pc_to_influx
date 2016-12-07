package servlets;

import config.config;
import config.readConfig;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@WebServlet("/")
public class to_influx extends HttpServlet {
    //TODO пробный вариант с статическим подключение к БД, надо переделать по человечески.
    private static InfluxDB influxDB = null;
    private static config cfg = null;
    private static String rootPath = null;

    public static void Init() throws IOException {

        to_influx.cfg = readConfig.parceConfig( "C:\\influx.json");

        InfluxDB influxDB = InfluxDBFactory.connect(cfg.getDbURL(), cfg.getLogin(), cfg.getPassword());
        influxDB.createDatabase(cfg.getDbName());

        // Flush every 2000 Points, at least every 100ms
        influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS);

        System.out.println("#  Connected to InfluxDB Version: " + influxDB.version() + " #");
    }
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        if (to_influx.influxDB == null)
            to_influx.Init();

        /*Параметры по списку
        name  host status start_time end_time think_time waisted_time
        */

        Point point1 = Point.measurement(request.getParameterValues("name")[0])
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("host", request.getParameterValues("host")[0])
                .addField("status", request.getParameterValues("status")[0])
                .addField("start_time", request.getParameterValues("start_time")[0])
                .addField("end_time", request.getParameterValues("end_time")[0])
                .addField("think_time", request.getParameterValues("think_time")[0])
                .addField("waisted_time", request.getParameterValues("waisted_time")[0])
                .build();

        influxDB.write(cfg.getDbName(), "autogen", point1);

        response.setContentType("text/html");
        response.setCharacterEncoding("windows-1251");
        PrintWriter out = response.getWriter();

        out.write("");
        out.flush();
    }
}
