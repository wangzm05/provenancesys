/**
* PROPRIETARY RIGHTS STATEMENT
* The contents of this file represent confidential information that is the
* proprietary property of Active Endpoints, Inc.  Viewing or use of
* this information is prohibited without the express written consent of
* Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
* is strictly forbidden. Copyright (c) 2002-2008 All rights reserved.
*
* aeworkflow-util.js:
* Defines common util script.
*
* Dependencies:
* jquery.js, jquery.block.js (http://www.jquery.com, jQuery 1.1.2)
* jqModal.js (http://dev.iceburg.net/jquery/jqModal/)
*/

// fixme (PJ) add global ajax http error handler and special case for http 401/403.
var gAeSystemErrorMessage = "A system error has occured. Please contact your ActiveBPEL for People administrator.";
/** global debug flag. */
var gAeDebugMode = true;
/** default request time out, in ms. */
var gAeAjaxTimeoutMs = 180000;


/*
* Initializes the status message div.
*/
function aeInitStatusMessage()
{
   // hook in click event handler for 'show more details' link.
   $("#aeshowstatusmessagedetail").click( function() {
      $("#aestatusmessagedetail").show();
      $(this).hide();
      return false;
   });
}

function aeShowStatusMessage(aMessage)
{
   aeShowMessageWithIcon(aMessage, null, false);
}

function aeShowFailureMessage(aMessage)
{
   aeShowMessageWithIcon(aMessage, null, false);
}

function aeShowErrorMessage(aMessage)
{
   aeShowMessageWithIcon(gAeSystemErrorMessage, aMessage, true);
}

function aeShowMessageWithIcon(aMessage, aDetail, aShowIcon)
{
   // hide progress message.
   aeShowProgressMessage("");
   $("#aestatusmessagecontainer").removeClass("erroricon");
   if (aShowIcon == true)
   {
      $("#aestatusmessagecontainer").addClass("erroricon");
   }
   if (aMessage && aMessage != null && aMessage != "")
   {
      $("#aestatusmessage").text(aMessage).show();
      if (aDetail && aDetail != null && aDetail != "")
      {
         $("#aestatusmessagedetail").val(aDetail).hide();
         $("#aeshowstatusmessagedetail").show();
         $("#aestatusmessagedetailcontainer").show();
      }
      $("#aestatusmessagecontainer").show();
   }
   else
   {
      $("#aestatusmessage").html("")
      $("#aestatusmessagedetail").val("");
      $("#aestatusmessagedetailcontainer").hide();
      $("#aestatusmessagecontainer").hide();
   }
}
// end aeShowStatusMessage()

function aeShowProgressMessage(aMessage)
{
   if (aMessage && aMessage != null && aMessage != "")
   {
      $("#aeprogressmessage span").html(aMessage);
      $("#aeprogressmessage").show();
   }
   else
   {
      $("#aeprogressmessage span").html("");
      $("#aeprogressmessage").hide();
   }
}
// end aeShowProgressMessage()

/**
 * Returns true if the xml response is a valid dom and
 * and has the status code element. Otherwise, assume
 * session has been invalidated and the login form is
 * presented instead of the xml response dom.
 */
function aeIsXmlResponseValid(aXml)
{
   var code = parseInt($("statuscode", aXml).text());
   // test to see if the expected result was XML and had a status code page.
   // otherwise (code is NaN), assume the xml result is actually a text/html  response
   // e.g for a login screen (i.e. session timed out).
   if (isNaN(code))
   {
      // session time out. Reload current page (and i.e. show login page)
      window.location.reload();
      return false;
   }
   return true;
}
// end aeIsXmlResponseValid

/**
 * displays ajax http related response page in a new window. Use for debuging.
 */
function aeShowAjaxHttpError(aAjaxRequest)
{
   // show http error in a new window except for 401/403 auth challenge related responses.
   if (aeIsNotNullOrUndefined(aAjaxRequest) && aeIsNotNullOrUndefined(aAjaxRequest.responseText)
     &&  aAjaxRequest.status != 401 && aAjaxRequest.status != 403)
   {
      var errWin =  window.open("","aetaskhttperrorwin", "menubar=no,scrollbars=yes,resizable=yes");
      errWin.document.write(aAjaxRequest.responseText);
      errWin.document.close();
      errWin.focus();
   }
}
// end aeShowAjaxHttpError

function aeAjaxGetXml(aUrl, aDataMap, aTimeoutMs, aErrorHandlerFn, aSuccessHandlerFn)
{
   aeAjaxCall(aUrl, aDataMap, "GET", "xml", aTimeoutMs, aErrorHandlerFn, aSuccessHandlerFn);
}
// end aeAjaxGetXml()

function aeAjaxGetHtml(aUrl, aDataMap, aTimeoutMs, aErrorHandlerFn, aSuccessHandlerFn)
{
   aeAjaxCall(aUrl, aDataMap, "GET", "html", aTimeoutMs, aErrorHandlerFn, aSuccessHandlerFn);
}
// end aeAjaxGetHtml()

function aeAjaxCall(aUrl, aDataMap, aHttpMethod, aDataType, aTimeoutMs, aErrorHandlerFn, aSuccessHandlerFn)
{
   // Hook in HTTP error handler to show http error page in a new window
   var errDelegate = function(aRequest, aSettings) {
      aeShowAjaxHttpError(aRequest);
      aErrorHandlerFn(aRequest, aSettings);
   };

   // Hook in funtion to handle asynchronous xml response errors due to invalid sessions.
   var okDelegate = aSuccessHandlerFn;
   if ("xml" == aDataType)
   {
      okDelegate = function(aXmlDom) {
         if (aeIsXmlResponseValid(aXmlDom))
         {
            aSuccessHandlerFn(aXmlDom);
         }
      };
   }
   $.ajax(
      {
         url: aUrl,
         type: aHttpMethod,
         dataType: aDataType,
         timeout: aTimeoutMs,
         data: aDataMap,
         error: errDelegate,
         success: okDelegate
       }
    );
}
// end aeAjaxCall

function aeEnableById(aId, aEnable, aEnableClass, aDisableClass)
{
   var ele = $("#" + aId);
   if (aEnable)
   {
      ele.addClass(aEnableClass);
      ele.removeClass(aDisableClass);
   }
   else
   {
      ele.addClass(aDisableClass);
      ele.removeClass(aEnableClass);
   }
}
// aeEnableById()

function aeShowById(aId, aShow)
{
   var ele = $("#" + aId);
   if (aShow)
   {
      ele.show();
   }
   else
   {
      ele.hide();
   }
}
// aeShowById()

function aeShowByClass(aClass, aShow)
{
   var ele = $("." + aClass);
   if (aShow)
   {
      ele.show();
   }
   else
   {
      ele.hide();
   }
}
// aeShowById()

function aeMakeImageButton(aButtonId, aIconImgUrl, aEvtHandler, aShowText)
{
	if (aeIsNotNullOrUndefined(aShowText) && aShowText == true)
	{
		var btnEle = $("#" + aButtonId);
		aeInternalMakeImageButton(aButtonId, btnEle.text(), aIconImgUrl, aEvtHandler)
	}
	else
	{
   	aeInternalMakeImageButton(aButtonId, " &nbsp; ", aIconImgUrl, aEvtHandler)
   }
}
// aeMakeImageButton()



function aeInternalMakeImageButton(aButtonId, aButtonText, aIconImgUrl, aEvtHandler)
{
   var btnEle = $("#" + aButtonId);
   btnEle.html('<span class="imagebuttonicon">' + aButtonText + '</span>');
   btnEle.addClass("imagebutton");
   var bgurl = 'url("' + aIconImgUrl + '")';
   $("#" + aButtonId + " span.imagebuttonicon").css("background-image", bgurl);
   if (aeIsNotNullOrUndefined(aEvtHandler))
   {
      btnEle.unbind( "click" );
      btnEle.click(function(){
         if ($("#" + aButtonId).attr("className") != "imagebuttondis")
         {
            aEvtHandler(aButtonId);
         }
         return false;
      });
   }
}
// aeInternalMakeImageButton()

function aeMakeImageLink(linkId, aIconImgUrl, aDirection, aEvtHandler, aDisabledIconImgUrl)
{
   var linkEle = $("#" + linkId);
   linkEle.addClass("imagelink");
   var cname = "imagelinkinlineleftimg";
   if (aDirection == "right")
   {
      cname = "imagelinkinlinerightimg";
      linkEle.append('<span class="' + cname + '"></span>');
   }
   else
   {
      linkEle.prepend('<span class="' + cname + '"></span>');
   }
   var bgurl = 'url("' + aIconImgUrl + '")';
   $("#" + linkId + " span." + cname).css("background-image", bgurl);
   $("#" + linkId + " span." + cname).attr("disabledimg", aDisabledIconImgUrl);
   $("#" + linkId + " span." + cname).attr("enabledimg", aIconImgUrl);
   linkEle.attr("cname", cname);
   if (aeIsNotNullOrUndefined(aEvtHandler))
   {
      linkEle.unbind( "click" );
      linkEle.click(function(){
         if ($("#" + linkId).attr("className") != "imagelinkdis")
         {
            aEvtHandler(linkId);
         }
         return false;
      });
   }
}
// aeMakeImageLink()


function aeEnableImageButton(aButtonId, aEnable)
{
   aeEnableById(aButtonId, aEnable, "imagebutton", "imagebuttondis");
}
// aeEnableImageButton

function aeEnableImageLink(aLinkId, aEnable)
{
   aeEnableById(aLinkId, aEnable, "imagelink", "imagelinkdis");
   // get icon span classname.
   var cname = $("#" + aLinkId).attr("cname");
   // get enabled or disable image url
   var iconImgUrl = $("#" + aLinkId + " span." + cname).attr("enabledimg");
   if (aEnable == false)
   {
      iconImgUrl = $("#" + aLinkId + " span." + cname).attr("disabledimg");
   }
   // replace current css bg url.
   if (aeIsNotNullOrUndefined(iconImgUrl))
   {
      var bgurl = 'url("' + iconImgUrl + '")';
      $("#" + aLinkId + " span." + cname).css("background-image", bgurl);
   }
}
// aeEnableImageLink

function aeIsNullOrUndefined(aObject)
{
   if (typeof(aObject) == 'undefined' || aObject == null)
   {
      return true;
   }
   else
   {
      return false;
   }
}
// end aeIsNullOrUndefined()

function aeIsNotNullOrUndefined(aObject)
{
   return aeIsNullOrUndefined(aObject) == false;
}
// end aeIsNotNullOrUndefined()

function aeIsNullOrEmpty(aObject)
{
   return aeIsNullOrUndefined(aObject) || aObject == "";
}
// end aeIsNullOrEmpty

function aeIsNotNullOrEmpty(aObject)
{
   return aeIsNullOrEmpty(aObject) == false;
}
// end aeIsNotNullOrEmpty


function aeAssertNotNull(aObject, aMessage)
{
   if (typeof(aObject) == 'undefined')
   {
      aeAssert("[undefined object]: " + aMessage);
   }
   else if (aObject == null)
   {
      aeAssert("[null object]: " + aMessage);
   }
}
// end aeAssertNotNull()

function aeAssert(aMessage)
{
   if (gAeDebugMode == true)
   {
      alert(aMessage);
   }
}
// end aeAssert()

