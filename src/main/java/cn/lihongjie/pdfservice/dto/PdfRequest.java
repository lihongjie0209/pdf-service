package cn.lihongjie.pdfservice.dto;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.Margin;
import lombok.Data;

@Data

public class PdfRequest extends BaseRequest {

    /**
     * Display header and footer. Defaults to {@code false}.
     */
    public Boolean displayHeaderFooter;
    /**
     * HTML template for the print footer. Should use the same format as the {@code headerTemplate}.
     */
    public String footerTemplate;
    /**
     * Paper format. If set, takes priority over {@code width} or {@code height} options. Defaults to 'Letter'.
     */
    public String format;
    /**
     * HTML template for the print header. Should be valid HTML markup with following classes used to inject printing values
     * into them:
     * <ul>
     * <li> {@code "date"} formatted print date</li>
     * <li> {@code "title"} document title</li>
     * <li> {@code "url"} document location</li>
     * <li> {@code "pageNumber"} current page number</li>
     * <li> {@code "totalPages"} total pages in the document</li>
     * </ul>
     */
    public String headerTemplate;
    /**
     * Paper height, accepts values labeled with units.
     */
    public String height;
    /**
     * Paper orientation. Defaults to {@code false}.
     */
    public Boolean landscape;
    /**
     * Paper margins, defaults to none.
     */
    public Margin margin;
    /**
     * Whether or not to embed the document outline into the PDF. Defaults to {@code false}.
     */
    public Boolean outline;
    /**
     * Paper ranges to print, e.g., '1-5, 8, 11-13'. Defaults to the empty string, which means print all pages.
     */
    public String pageRanges;

    /**
     * Give any CSS {@code @page} size declared in the page priority over what is declared in {@code width} and {@code height}
     * or {@code format} options. Defaults to {@code false}, which will scale the content to fit the paper size.
     */
    public Boolean preferCSSPageSize;
    /**
     * Print background graphics. Defaults to {@code false}.
     */
    public Boolean printBackground;
    /**
     * Scale of the webpage rendering. Defaults to {@code 1}. Scale amount must be between 0.1 and 2.
     */
    public Double scale;
    /**
     * Whether or not to generate tagged (accessible) PDF. Defaults to {@code false}.
     */
    public Boolean tagged;
    /**
     * Paper width, accepts values labeled with units.
     */
    public String width;

    public void updatePdfOptions(Page.PdfOptions pdfOptions) {


        if (displayHeaderFooter != null) {

            pdfOptions.setDisplayHeaderFooter(displayHeaderFooter);
        }
        pdfOptions.setFooterTemplate(footerTemplate);
        pdfOptions.setFormat(format);
        pdfOptions.setHeaderTemplate(headerTemplate);
        pdfOptions.setHeight(height);
        if (landscape != null) {
            pdfOptions.setLandscape(landscape);
        }
        pdfOptions.setMargin(margin);

        if (outline != null) {
            pdfOptions.setOutline(outline);
        }
        pdfOptions.setPageRanges(pageRanges);

        if (preferCSSPageSize != null) {
            pdfOptions.setPreferCSSPageSize(preferCSSPageSize);
        }

        if (printBackground != null) {
            pdfOptions.setPrintBackground(printBackground);
        }

        if (scale != null) {
            pdfOptions.setScale(scale);
        }
        if (tagged != null) {
            pdfOptions.setTagged(tagged);
        }
        pdfOptions.setWidth(width);


    }
}
